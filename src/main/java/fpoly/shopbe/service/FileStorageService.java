package fpoly.shopbe.service;


import fpoly.shopbe.DTO.UploadedFileInfo;
import fpoly.shopbe.config.FileStorageProperties;
import fpoly.shopbe.exception.FileNotFoundException;
import fpoly.shopbe.exception.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path filelogoStorageLocation;
    private final Path fileProductImageStorageLocation;

    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.filelogoStorageLocation = Paths.get(fileStorageProperties.getUploadLogoDir())
                .toAbsolutePath().normalize();

        this.fileProductImageStorageLocation = Paths.get(fileStorageProperties.getUploadProductImageDir())
                .toAbsolutePath().normalize();

        try{
            Files.createDirectories(filelogoStorageLocation);
            Files.createDirectories(fileProductImageStorageLocation);
        }catch (Exception ex){
            throw new FileStorageException("Counld not create the directory where the upload files will be stored"
            , ex);
        }
    }
    public String storeLogoFile(MultipartFile file){
        return storeFile(filelogoStorageLocation, file);
    }

    public String storeProductImageFile(MultipartFile file){
        return storeFile(fileProductImageStorageLocation, file);
    }

    public UploadedFileInfo storeUploadedProductImageFile(MultipartFile file){
        return storeUploadFile(fileProductImageStorageLocation, file);
    }

    private String storeFile(Path location, MultipartFile file){
        UUID uuid = UUID.randomUUID();

        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = uuid.toString() + "." + ext;

        try {
            if(fileName.contains("..")){
                throw  new FileStorageException("Sorry! Filename contains invalid path sequence "+fileName);
            }

            Path targetLocation = location.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }catch (Exception ex){
            throw new FileStorageException("could not store file "+ fileName + ". Please try again", ex);
        }
    }

    private UploadedFileInfo storeUploadFile(Path location, MultipartFile file){
        UUID uuid = UUID.randomUUID();

        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String fileName = uuid.toString() + "." + ext;

        try {
            if(fileName.contains("..")){
                throw  new FileStorageException("Sorry! Filename contains invalid path sequence "+fileName);
            }

            Path targetLocation = location.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            UploadedFileInfo info = new UploadedFileInfo();
            info.setFileName(fileName);
            info.setUid(uuid.toString());
            info.setName(StringUtils.getFilename(file.getOriginalFilename()));

            return info;
        }catch (Exception ex){
            throw new FileStorageException("could not store file "+ fileName + ". Please try again", ex);
        }
    }

    public Resource loadLogoFileAsResource(String filename){
        return loadFileAsResource(filelogoStorageLocation, filename);
    }

    public Resource loadProductImageFileAsResource(String filename){
        return loadFileAsResource(fileProductImageStorageLocation, filename);
    }
    private Resource loadFileAsResource(Path location, String filename){
        try {
            Path filePath = location.resolve(filename).normalize();

            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists()){
                return resource;
            }else {
                throw new FileNotFoundException("File not found "+ filename);
            }
        }catch (Exception e){
            throw new FileNotFoundException("File not found "+ filename, e);
        }
    }

    public void deleteLogoFile(String filename){
        deleteFile(filelogoStorageLocation, filename);
    }

    public void deleteProductImageFile(String filename){
        deleteFile(fileProductImageStorageLocation, filename);
    }
    private void deleteFile(Path location, String filename){
        try {
            Path filePath = location.resolve(filename).normalize();

            if(!Files.exists(filePath)){
                throw new FileNotFoundException("File not found "+ filename);
            }
            Files.delete(filePath);
        }catch (Exception e){
            throw new FileNotFoundException("File not found "+ filename, e);
        }
    }
}
