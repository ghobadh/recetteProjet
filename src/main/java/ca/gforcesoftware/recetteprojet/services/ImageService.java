package ca.gforcesoftware.recetteprojet.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author gavinhashemi on 2024-10-18
 */
public interface ImageService {
    void saveImage(String RecetteID, MultipartFile file);
}
