package fr.lauparr.project_planner.server.projections;

import java.util.List;

public interface GroupeTacheDTO {

  Long getId();

  String getNom();

  List<TacheDTO> getTaches();

}
