package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.exceptions.BadRequestException;
import ca.gforcesoftware.recetteprojet.services.ImageService;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author gavinhashemi on 2024-10-18
 */
public class ImageControllerTest {


    @Mock
    ImageService imageService;

    @Mock
    RecetteService recetteService;

    ImageController imageController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        imageController = new ImageController(imageService,recetteService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void testSaveImage() throws Exception {
        RecetteCommand recetteCommand = new RecetteCommand();
        recetteCommand.setId(22L);

        when(recetteService.findCommandById(anyLong())).thenReturn(recetteCommand);

        mockMvc.perform(get("/recette/22/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recette"));

        verify(recetteService, times(1)).findCommandById(anyLong());
    }

    @Test
    public void testHandleImagePost() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("imagefile",
                "IMG_1997.jpg", "image/jpeg", "test".getBytes());

        mockMvc.perform(multipart("/recette/1/image").file(mockMultipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recette/1/show"));

        verify(imageService,times(1)).saveImage(anyLong(),any());

    }

    @Test
    public void testRenderImage() throws Exception {
        RecetteCommand recetteCommand = new RecetteCommand();
        recetteCommand.setId(22L);
        String s = "This is a test for the rendering the image";
        Byte[] bytes = new Byte[s.getBytes().length];
        int i = 0;
        for (byte b : s.getBytes()) {
            bytes[i++] = b;
        }
        recetteCommand.setImage(bytes);

        when(recetteService.findCommandById(anyLong())).thenReturn(recetteCommand);

        MockHttpServletResponse response = mockMvc.perform(get("/recette/1/recetteimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        //I pull the image from the http servlet response
       byte[] responseByte = response.getContentAsByteArray();
       assertEquals(s.getBytes().length, responseByte.length);

    }


    @Test
    public void testHandleException() throws Exception {
        mockMvc.perform(get("/recette/6e/recetteimage"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }
}