package fr.lauparr.project_planner.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectionService {

  @Autowired
  private ProjectionFactory projectionFactory;

  public <T> T convertToDto(Object data, Class<T> clazz) {
    return projectionFactory.createProjection(clazz, data);
  }

  public <T> List<T> convertToDto(List<?> liste, Class<T> clazz) {
    return liste.stream().map(x -> projectionFactory.createProjection(clazz, x)).collect(Collectors.toList());
  }

}
