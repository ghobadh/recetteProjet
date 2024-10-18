package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.services.ImageService;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gavinhashemi on 2024-10-18
 */
@Slf4j
@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecetteService recetteService;


    public ImageController(ImageService imageService, RecetteService recetteService) {
        this.imageService = imageService;
        this.recetteService = recetteService;
    }


    @GetMapping("/recette/{id}/image")
    public String showImage(@PathVariable Long id, Model model) {
        model.addAttribute("recette", recetteService.findCommandById(id));
        return "recette/uploadingimageform";
    }


    /*
    Note that the recetteimage is in show.html of recette and we render it whenever the page is refreshed
     */
    @GetMapping("/recette/{id}/recetteimage")
    public void renderRecetteImageFromDB(@PathVariable Long id, HttpServletResponse response)  throws IOException {
        RecetteCommand  recetteCommand = recetteService.findCommandById(id);
        if (recetteCommand != null) {
            byte[] byteImage = new byte[recetteCommand.getImage().length];

            int i = 0 ;
            for(Byte b:recetteCommand.getImage()){
                byteImage[i++]=b; //auto boxing

            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteImage);
            //This is Tomcat utility to copy bytes which is here loading the image into the recetteimage
            IOUtils.copy(is, response.getOutputStream());
;        }
    }

    @PostMapping("/recette/{id}/image")
    public String saveImage(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
        log.info("Saving image to DB");
        log.info("Recette id {}", id);
        imageService.saveImage(id, file);

        return "redirect:/recette/" + id +"/show";
    }
}
