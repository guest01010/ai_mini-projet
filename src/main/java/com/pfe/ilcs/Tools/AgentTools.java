package com.pfe.ilcs.Tools;

import com.pfe.ilcs.Entities.Etudiant;
import com.pfe.ilcs.Reposetories.EtudiantRepo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgentTools {
    @Autowired
private EtudiantRepo ep;

    @Tool(description = "Récupérer les informations d'un étudiant par apogée")
    List<Etudiant> getParApoge(@ToolParam(description = "L'apogée de l'étudiant") String nom){
        return ep.findByApogee(nom);
    }
    @Tool(description = "Récupérer les informations d'un étudiant par filière")
    List<Etudiant> getParfilier(@ToolParam(description = "La filière de l'étudiant") String nom){
        return ep.findByFilliere(nom);
    }
    @Tool(description = "Récupérer les informations d'un étudiant par nom ou prénom")
    List<Etudiant> getParNomouPRenom(@ToolParam(description = "Le nom ou prénom de l'étudiant") String nomOuPrenom){
        return ep.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(nomOuPrenom,nomOuPrenom);
    }
}
