package tn.esprit.tnfoyer.services.implementation;

import org.springframework.stereotype.Service;
import tn.esprit.tnfoyer.dto.EtudiantDTO;
import tn.esprit.tnfoyer.entities.Etudiant;
import tn.esprit.tnfoyer.mapper.EtudiantMapper;
import tn.esprit.tnfoyer.repositories.EtudiantRepository;
import tn.esprit.tnfoyer.services.interfaces.IEtudiantService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtudiantService implements IEtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final EtudiantMapper etudiantMapper;

    public EtudiantService(EtudiantRepository etudiantRepository, EtudiantMapper etudiantMapper) {
        this.etudiantRepository = etudiantRepository;
        this.etudiantMapper = etudiantMapper;
    }

    @Override
    public Etudiant addEtudiant(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    @Override
    public String updateEtudiant(Etudiant etudiant) {
        Etudiant existing = etudiantRepository.getById(etudiant.getIdEtudiant());
        if (existing != null) {
            etudiantRepository.save(etudiant);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public String deleteEtudiant(Etudiant etudiant) {
        Etudiant existing = etudiantRepository.getById(etudiant.getIdEtudiant());
        if (existing != null) {
            etudiantRepository.delete(existing);
            return "successfully updated";
        } else {
            return "not found";
        }
    }

    @Override
    public Object getEtudiant(Long idEtudiant) {
        Etudiant existing = etudiantRepository.findById(idEtudiant).get();
        if (existing != null) {
            return existing;
        } else {
            return "Etudiant not found";
        }
    }

    @Override
    public List<Etudiant> getAllEtudiant() {
        return etudiantRepository.findAll();
    }

    @Override
    public EtudiantDTO addOrUpdateEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = etudiantMapper.toEntity(etudiantDTO);
        Etudiant saved = etudiantRepository.save(etudiant);
        return etudiantMapper.toDto(saved);
    }

    @Override
    public List<EtudiantDTO> findAllEtudiants() {
        return etudiantRepository.findAll()
                .stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EtudiantDTO findById(long idEtudiant) {
        Etudiant etudiant = etudiantRepository.findById(idEtudiant)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        return etudiantMapper.toDto(etudiant);
    }

}
