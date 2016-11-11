package br.com.fabricadeprogramador.javaneiros;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.logging.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@SpringBootApplication
public class JavaneirosApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaneirosApplication.class, args);

	}

	@Bean
	CommandLineRunner runner(ClienteRepository repository) {
		return new CommandLineRunner() {

			@Override
			public void run(String... arg0) throws Exception {

				Arrays.asList(new Cliente("Jão"), new Cliente("Zé"), new Cliente("Maria")).forEach(cli -> {
					repository.save(cli);
				});

				repository.findAll().forEach(cli -> {
					System.out.println(cli.getNome());
				});

			}
		};
	}
}

@Data
@Entity
class Cliente {

	@Id
	@GeneratedValue
	private Integer id;

	private String nome;

	Cliente(String nome) {
		this.nome = nome;
	}

	Cliente() {

	}

}

interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	
	public List<Cliente> findByNome(String nome);
}

@RestController
class ClienteRestController{
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@PostMapping("clientes/")
	private Cliente novo(@RequestBody Cliente cli){
		return clienteRepository.save(cli);
	}
	
	@GetMapping("clientes/")
	private List<Cliente> buscarTodos(){
		return clienteRepository.findAll();
	}	
	
	
	@GetMapping("clientes/{nome}")
	private List<Cliente> buscarPorNome(@PathVariable("nome")  String nome){
		return clienteRepository.findByNome(nome);
	}	
	
	
	@PutMapping("clientes/")
	private Cliente alterar(@RequestBody Cliente cli){
		return clienteRepository.save(cli);
	}
	
	@DeleteMapping("clientes/{id}")
	private void remover(@PathVariable("id") Integer id){
		
		clienteRepository.delete(id);
		
	}
	
	
}
