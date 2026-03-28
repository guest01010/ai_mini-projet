package com.pfe.ilcs.Reposetories;

import com.pfe.ilcs.Entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtudiantRepo extends JpaRepository<Etudiant, String> {
List<Etudiant> findByFilliere(String nom);
List<Etudiant> findByApogee(String apogee);
List<Etudiant> findByNiveau(String niveau);
List<Etudiant> findByFilliereAndNiveau(String filliere, String niveau);
List<Etudiant> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom);
}
