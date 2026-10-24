package dowglas.app.controller;

import dowglas.app.dto.UrlRequestDTO;
import dowglas.app.exception.ProcessBuilderException;
import dowglas.app.service.YoutubeConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class YoutubeConvertController {
    @Autowired
    YoutubeConvertService youtubeConvertService;

    @PostMapping("/download")
    public ResponseEntity<String> convert(@RequestBody UrlRequestDTO urlRequestDTO) throws ProcessBuilderException {
        return ResponseEntity.ok(youtubeConvertService.convert(urlRequestDTO.url()));
    }
}
