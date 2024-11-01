package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author gavinhashemi on 2024-10-18
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecetteRepository recetteRepository;

    public ImageServiceImpl(RecetteRepository recetteRepository) {
        this.recetteRepository = recetteRepository;
    }

    /*
    Since all operations on the picture is primitive byte and not Byte object,
    when I save it , I need to autobox to the array byte.
    then we save it in recette Repository.

    Note that we need to add these two properties
            spring.servlet.multipart.max-file-size=10MB
            spring.servlet.multipart.max-request-size=10MB

    otherwise the multipart will fail to download the image.
     */

    @Override
    @Transactional
    public void saveImage(String RecetteID, MultipartFile file) {
        try {
            Recette recette = recetteRepository.findById(RecetteID).get();

            Byte[] fileBytes = new Byte[file.getBytes().length];

            int i = 0;
            for(byte b : file.getBytes()) {
                fileBytes[i++] = b;
            }
            recette.setImage(fileBytes);
            recetteRepository.save(recette);

        } catch (IOException ex){
            log.error(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
