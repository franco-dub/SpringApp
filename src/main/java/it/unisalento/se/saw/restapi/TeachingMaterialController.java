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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import it.unisalento.se.saw.IService.TeachingMaterialIService;
import it.unisalento.se.saw.domain.TeachingMaterial;
import it.unisalento.se.saw.dto.TeachingMaterialDto;
import it.unisalento.se.saw.dto.TicketDto;
import it.unisalento.se.saw.exceptions.CustomErrorType;
import it.unisalento.se.saw.repo.TeachingMaterialRepository;
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
	
	@RequestMapping(value = "/uploadFile/{moduleId}", method = RequestMethod.POST, 
			consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> uploadFile(@PathVariable("moduleId") Integer moduleId,
			@RequestParam("file") MultipartFile file) {
		TeachingMaterial dbFile = tmService.storeFile(moduleId, file);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(dbFile.getTechingMaterialId().toString())
//                .toUriString();

        return new ResponseEntity<TeachingMaterial>(dbFile, HttpStatus.CREATED);

    }
	
	@RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer fileId) {
        // Load file from database
        TeachingMaterial dbFile = tmService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getDoc()));
    }
	
	@RequestMapping(value = "/findByModule/{moduleId}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> findByModule(@PathVariable Integer moduleId){
		List<TeachingMaterial> lista = tmService.findByModule(moduleId);
		if (lista.isEmpty()) {
    		return new ResponseEntity<>(new CustomErrorType("List empty."),
        			HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
    		//NO_CONTENT doesn't print json error
    	}
		return new ResponseEntity<List<TeachingMaterial>>(lista, HttpStatus.OK);
	}
}
