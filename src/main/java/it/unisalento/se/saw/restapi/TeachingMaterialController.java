package it.unisalento.se.saw.restapi;

import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.unisalento.se.saw.IService.TeachingMaterialIService;
import it.unisalento.se.saw.domain.TeachingMaterial;
import it.unisalento.se.saw.dto.TeachingMaterialDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;

@MultipartConfig
@CrossOrigin
@RestController
@RequestMapping(path = "/teachingMaterial")
public class TeachingMaterialController {

	TeachingMaterialIService tmService;
	@Autowired
	public TeachingMaterialController(TeachingMaterialIService tmService) {
		super();
		this.tmService = tmService;
	}
	//------------------- Upload File --------------------------------------------------------
	@RequestMapping(value = "/uploadFile/{moduleId}", method = RequestMethod.POST, 
			consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> uploadFile(@PathVariable("moduleId") Integer moduleId,
			@RequestParam("file") MultipartFile file) {
		try {
			TeachingMaterial dbFile = tmService.storeFile(moduleId, file);
			return new ResponseEntity<TeachingMaterial>(dbFile, HttpStatus.CREATED);
		} catch(Exception e) {
			System.out.println(e.toString());
    		return new ResponseEntity<>(new CustomErrorType("Unable to upload the file!"),
    				HttpStatus.BAD_REQUEST);
    	}
    }
	//------------------- Download File --------------------------------------------------------
	@RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) {
        // Load file from database
        TeachingMaterial dbFile = tmService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getDoc()));
    }
	//------------------- Find details by Module --------------------------------------------------------
	@RequestMapping(value = "/findByModule/{moduleId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> findByModule(@PathVariable Integer moduleId){
		List<TeachingMaterialDto> lista = tmService.findByModule(moduleId);
		if (lista.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
    	}
		return new ResponseEntity<List<TeachingMaterialDto>>(lista, HttpStatus.OK);
	}
	//------------------- Delete File --------------------------------------------------------
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFile(@PathVariable("id") Integer id) {
    	System.out.println("Fetching & Deleting File with id " + id);
        try {
        	tmService.deleteFileById(id);
            return new ResponseEntity<TeachingMaterial>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return new ResponseEntity<>(new CustomErrorType("Unable to delete! File with id " + id 
                    + " not found."), HttpStatus.NOT_FOUND);
        }
    }
}
