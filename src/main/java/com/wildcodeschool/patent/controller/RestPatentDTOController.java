package com.wildcodeschool.patent.controller;

import com.wildcodeschool.patent.DTO.PatentDTO;
import com.wildcodeschool.patent.repository.UserRepository;
import com.wildcodeschool.patent.service.EPORequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


/**
 * Rest Controller to send patent Object in Json to front
 */
@RestController
@RequestMapping("/api/patent")
public class RestPatentDTOController {

    @Autowired
    EPORequestService epoRequestService;

    @Autowired
    private UserRepository userRepository;

    /**
     *  method to send front patent data with param patentNumber
     * @param patentNumber
     * @return
     * @throws Exception
     */
    @GetMapping("/{patentNumber}")
    public ResponseEntity<PatentDTO> getPatentByPatentNumber(@PathVariable("patentNumber") String patentNumber, Authentication authentication) throws Exception {
        PatentDTO patent = epoRequestService.getPatentByNumber(patentNumber, authentication);
        if (patent == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(patent, HttpStatus.OK);
    }

    @GetMapping("/search/{query}/{pageNumber}")
    public ResponseEntity<List<PatentDTO>> getPatentsByKeyword(@PathVariable("query") String query, @PathVariable("pageNumber") Integer pageNumber, Authentication authentication) throws Exception {
        try {
            List<PatentDTO> patentDTOList = epoRequestService.getPatentsByQuery(query, authentication, pageNumber);
            return new ResponseEntity<>(patentDTOList, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
