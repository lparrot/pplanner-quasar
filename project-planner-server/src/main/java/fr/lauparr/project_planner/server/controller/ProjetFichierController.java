package fr.lauparr.project_planner.server.controller;

import fr.lauparr.project_planner.server.model.Projet;
import fr.lauparr.project_planner.server.model.ProjetFichier;
import fr.lauparr.project_planner.server.projections.ProjetDTO;
import fr.lauparr.project_planner.server.repository.ProjetFichierRepository;
import fr.lauparr.project_planner.server.repository.ProjetRepository;
import fr.lauparr.project_planner.server.service.ProjectionService;
import lombok.Data;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projets/fichiers")
public class ProjetFichierController {

  @Autowired
  private ProjetRepository projetRepository;
  @Autowired
  private ProjetFichierRepository projetFichierRepository;
  @Autowired
  private ProjectionService projectionService;

  @PostMapping("/{id}")
  public ResponseEntity postFichiers(@PathVariable Long id, MultipartHttpServletRequest request) {
    Projet projet = projetRepository.findById(id).orElse(null);
    if (projet == null) {
      return ResponseEntity.notFound().build();
    }
    List<String> nomFichiers = projetFichierRepository.findAllNomFichierByProjet(id);
    Iterator<String> filenames = request.getFileNames();
    while (filenames.hasNext()) {
      MultipartFile file = request.getFile(filenames.next());
      String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      if (nomFichiers.stream().noneMatch(fileName::equals)) {
        try {
          ProjetFichier projetFichier = new ProjetFichier(fileName, file.getContentType(), file.getBytes());
          projet.addFichier(projetFichier);
          projetFichierRepository.save(projetFichier);
        } catch (IOException ignored) {
        }
      }
    }
    return ResponseEntity.ok(projectionService.convertToDto(projet, ProjetDTO.class));
  }

  @PostMapping(value = "/download", produces = "application/octet-stream")
  public ResponseEntity download(@RequestBody List<PostProjetFichierDownloadParams> params) throws IOException, ArchiveException {

    byte[] binary = new byte[0];
    String filename = "";

    if (params.size() == 1) {
      ProjetFichier fichier = projetFichierRepository.getOne(params.get(0).getId());
      filename = fichier.getNom();
      binary = fichier.getData();
    } else {
      filename = "archive.zip";
      ByteArrayOutputStream archiveStream = new ByteArrayOutputStream();
      ArchiveOutputStream archive = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, archiveStream);


      for (PostProjetFichierDownloadParams param : params) {
        List<ProjetFichier> fichiers = projetFichierRepository.findAllById(params.stream().map(PostProjetFichierDownloadParams::getId).collect(Collectors.toList()));
        for (ProjetFichier fichier : fichiers) {
          ZipArchiveEntry entry = new ZipArchiveEntry(fichier.getNom());
          archive.putArchiveEntry(entry);

          BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(fichier.getData()));

          IOUtils.copy(input, archive);
          input.close();
          archive.closeArchiveEntry();
        }
      }

      archive.finish();
      binary = archiveStream.toByteArray();
      archiveStream.close();
    }

    return ResponseEntity.ok()
      .header("filename", filename)
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .contentLength(binary.length)
      .body(binary);
  }

  @Data
  private static class PostProjetFichierDownloadParams {
    private Long id;
  }


}
