package com.facens.ac2.config;

import com.facens.ac2.petcare.dto.CreateAnimalRequest;
import com.facens.ac2.petcare.dto.CreateConsultaRequest;
import com.facens.ac2.petcare.dto.CreateTutorRequest;
import com.facens.ac2.petcare.dto.CreateVacinaRequest;
import com.facens.ac2.petcare.dto.CreateVeterinarioRequest;
import com.facens.ac2.petcare.dto.CreateRegistroVacinacaoRequest;
import com.facens.ac2.petcare.entity.Especialidade;
import com.facens.ac2.petcare.entity.PorteAnimal;
import com.facens.ac2.petcare.repository.AnimalRepository;
import com.facens.ac2.petcare.repository.ConsultaRepository;
import com.facens.ac2.petcare.repository.TutorRepository;
import com.facens.ac2.petcare.repository.VacinaRepository;
import com.facens.ac2.petcare.repository.VeterinarioRepository;
import com.facens.ac2.petcare.service.AnimalService;
import com.facens.ac2.petcare.service.ConsultaService;
import com.facens.ac2.petcare.service.RegistroVacinacaoService;
import com.facens.ac2.petcare.service.TutorService;
import com.facens.ac2.petcare.service.VacinaService;
import com.facens.ac2.petcare.service.VeterinarioService;
import com.facens.ac2.projetos.dto.CreateFuncionarioRequest;
import com.facens.ac2.projetos.dto.CreateProjetoRequest;
import com.facens.ac2.projetos.dto.CreateSetorRequest;
import com.facens.ac2.projetos.repository.FuncionarioRepository;
import com.facens.ac2.projetos.repository.ProjetoRepository;
import com.facens.ac2.projetos.repository.SetorRepository;
import com.facens.ac2.projetos.service.FuncionarioService;
import com.facens.ac2.projetos.service.ProjetoService;
import com.facens.ac2.projetos.service.SetorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner seedDatabase(
            SetorRepository setorRepository,
            FuncionarioRepository funcionarioRepository,
            ProjetoRepository projetoRepository,
            SetorService setorService,
            FuncionarioService funcionarioService,
            ProjetoService projetoService,

            TutorRepository tutorRepository,
            AnimalRepository animalRepository,
            VeterinarioRepository veterinarioRepository,
            ConsultaRepository consultaRepository,
            VacinaRepository vacinaRepository,
            TutorService tutorService,
            AnimalService animalService,
            VeterinarioService veterinarioService,
            ConsultaService consultaService,
            VacinaService vacinaService,
            RegistroVacinacaoService registroVacinacaoService
    ) {
        return args -> {

            /*
             * =====================================================
             * PARTE 1 - SISTEMA DE CONTROLE DE PROJETOS
             * =====================================================
             */

            if (setorRepository.count() == 0) {
                setorService.cadastrar(new CreateSetorRequest("Tecnologia"));
                setorService.cadastrar(new CreateSetorRequest("Recursos Humanos"));
                setorService.cadastrar(new CreateSetorRequest("Financeiro"));
            }

            if (funcionarioRepository.count() == 0) {
                funcionarioService.cadastrar(new CreateFuncionarioRequest("William Oliveira", 1L));
                funcionarioService.cadastrar(new CreateFuncionarioRequest("Ana Souza", 1L));
                funcionarioService.cadastrar(new CreateFuncionarioRequest("Carlos Pereira", 2L));
            }

            if (projetoRepository.count() == 0) {
                projetoService.cadastrar(new CreateProjetoRequest(
                        "Sistema Interno de Gestão",
                        LocalDate.now().plusDays(1),
                        LocalDate.now().plusDays(30)
                ));

                projetoService.cadastrar(new CreateProjetoRequest(
                        "Portal de Atendimento ao Cliente",
                        LocalDate.now().plusDays(5),
                        LocalDate.now().plusDays(45)
                ));

                projetoService.vincularFuncionario(1L, 1L);
                projetoService.vincularFuncionario(1L, 2L);
                projetoService.vincularFuncionario(2L, 3L);
            }

            /*
             * =====================================================
             * PARTE 2 - SISTEMA PETCARE
             * =====================================================
             */

            if (tutorRepository.count() == 0) {
                tutorService.cadastrar(new CreateTutorRequest(
                        "Carlos Tutor",
                        "carlos@email.com",
                        "15999990000"
                ));

                tutorService.cadastrar(new CreateTutorRequest(
                        "Mariana Lima",
                        "mariana@email.com",
                        "15999991111"
                ));
            }

            if (animalRepository.count() == 0) {
                animalService.cadastrar(new CreateAnimalRequest(
                        "Rex",
                        "Cachorro",
                        "Golden Retriever",
                        5,
                        PorteAnimal.GRANDE,
                        1L
                ));

                animalService.cadastrar(new CreateAnimalRequest(
                        "Mimi",
                        "Gato",
                        "Siamês",
                        3,
                        PorteAnimal.PEQUENO,
                        2L
                ));
            }

            if (veterinarioRepository.count() == 0) {
                veterinarioService.cadastrar(new CreateVeterinarioRequest(
                        "Dra. Ana",
                        "CRMV-SP-12345",
                        Especialidade.CLINICA_GERAL
                ));

                veterinarioService.cadastrar(new CreateVeterinarioRequest(
                        "Dr. Bruno",
                        "CRMV-SP-99999",
                        Especialidade.DERMATOLOGIA
                ));

                veterinarioService.cadastrar(new CreateVeterinarioRequest(
                        "Dra. Vacina",
                        "CRMV-SP-77777",
                        Especialidade.VACINACAO
                ));
            }

            if (consultaRepository.count() == 0) {
                consultaService.agendar(new CreateConsultaRequest(
                        LocalDateTime.now().plusDays(7).withHour(14).withMinute(0).withSecond(0).withNano(0),
                        Especialidade.CLINICA_GERAL,
                        "Consulta de rotina para avaliação geral.",
                        1L,
                        1L
                ));

                consultaService.agendar(new CreateConsultaRequest(
                        LocalDateTime.now().plusDays(8).withHour(10).withMinute(30).withSecond(0).withNano(0),
                        Especialidade.DERMATOLOGIA,
                        "Avaliação dermatológica.",
                        2L,
                        2L
                ));
            }

            if (vacinaRepository.count() == 0) {
                vacinaService.cadastrar(new CreateVacinaRequest(
                        "V10",
                        "PetVac",
                        12
                ));

                vacinaService.cadastrar(new CreateVacinaRequest(
                        "Antirrábica",
                        "BioPet",
                        12
                ));

                registroVacinacaoService.registrar(new CreateRegistroVacinacaoRequest(
                        LocalDate.now(),
                        "Vacinação aplicada sem reação adversa.",
                        1L,
                        1L,
                        3L
                ));
            }
        };
    }
}