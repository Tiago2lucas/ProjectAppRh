package com.AppRh.AppRh.Controlle;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRh.AppRh.Repository.CandidatoRepository;
import com.AppRh.AppRh.Repository.VagaRepository;
import com.AppRh.AppRh.models.Candidato;
import com.AppRh.AppRh.models.Vaga;

@Controller
public class VagaControlle {

	@Autowired
	private VagaRepository vr;

	@Autowired
	private CandidatoRepository cr;

	@RequestMapping(value = "/cadastrarVaga", method = RequestMethod.GET)
	public String form() {
		return "vaga/formVaga";
	}

	// CADASTRAR VAGA
	@RequestMapping(value = "/cadastrarVaga", method = RequestMethod.POST)
	public String form(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {

		// Caso de resultado da validação tenha um erro no formulario
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarVaga";

		}

		// O Validação estive sem erros, e o formulario for enviado com sucesso
		vr.save(vaga);
		attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso");
		return "redirect:/cadastrarVaga";
	}

	// LISTA VAGAS
	@RequestMapping("/vagas")
	public ModelAndView listaVaga() {
		ModelAndView mv = new ModelAndView("vaga/listaVaga");
		// Percorre uma List de vaga, ou seja pega todas as vagas.
		Iterable<Vaga> vagas = vr.findAll();
		mv.addObject("vagas", vagas);
		return mv;
	}

	// LISTA CANDIDATOS

	// DETALHES DE VAGA 
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView detalhesVaga(@PathVariable("codigo") long codigo) {

		Vaga vaga = vr.findByCodigo(codigo);

		ModelAndView mv = new ModelAndView("/vaga/detalhesVaga");
		mv.addObject("vaga", vaga);

		// Lista os candidados que esta vinculado a Vaga
		Iterable<Candidato> candidatos = cr.findByVaga(vaga);

		
		mv.addObject("candidatos", candidatos);

		return mv;

	}

	// MÉTODO ADICIONA CANDIDADO
	@RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
	public String detalhesVagaPost(@PathVariable("codigo") long codigo, @Valid Candidato candidato,
			BindingResult result, RedirectAttributes attributes) {

		// Resultado da Validação
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/{codigo}";
		}

		// rg duplicado
		if (cr.findByRg(candidato.getRg()) != null) {
			attributes.addFlashAttribute("mensagem_erro", " RG duplicado");
			return "redirect:/{codigo}";
		}

		// Salva o candidato a uma vaga.
		Vaga vaga = vr.findByCodigo(codigo);
		candidato.setVaga(vaga);
		cr.save(candidato);
		attributes.addFlashAttribute("mensagem", "Candidato adicionado com Sucesso!");
		return "redirect:/{codigo}";

	}

	/* MÉTODOS DE DELET */
	// DELETA VAGA
	@RequestMapping("/deletarVaga")
	public String deletarVaga(long codigo) {
		Vaga vaga = vr.findByCodigo(codigo);
		vr.delete(vaga);

		return "redirect:/vagas";

	}

	// DELETA CANDIDATO

	public String deletaCandidato(String rg) {
		// Consulto o rg do candidado no banco de dados
		Candidato candidato = cr.findByRg(rg);

		// Busca essa vaga qu esse candidato esta relacionado
		Vaga vaga = candidato.getVaga();

		// Obtém o código da vaga
		String codigo = "" + vaga.getCodigo();

		// Exclui o candidato do banco de dados
		cr.delete(candidato);

		// Redireciona para a página da vaga correspondente ao código
		return "redirect:/" + codigo;
	}

	/* MÉTODO QUE FAZEM UPDATE DA VAGA */

	// FORMULÁRIO DA VAGA
	@RequestMapping(value = "/editar-vaga", method = RequestMethod.GET)
	public ModelAndView editarVaga(long codigo) {
		// Consulta a vaga no banco de dados usando o código fornecido
		Vaga vaga = vr.findByCodigo(codigo);

		// Cria um objeto ModelAndView associado à página "vaga/update-vaga
		ModelAndView mv = new ModelAndView("vaga/update-vaga");

		// Adiciona a vaga ao objeto ModelAndView com o nome "vaga"
		mv.addObject("vaga", vaga);
		// Retorna o objeto ModelAndView
		return mv;
	}

	// UPDATE VAGA
	@RequestMapping(value = "/editar-vaga", method = RequestMethod.POST)
	public String updateVaga(@Valid Vaga vaga, BindingResult result, RedirectAttributes attributes) {

		// Salva a vaga no banco de dados
		vr.save(vaga);

		// Adiciona uma mensagem de sucesso aos atributos de redirecionamento
		attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

		// Obtém o código da vaga
		long codigoLog = vaga.getCodigo();
		String codigo = "" + codigoLog;

		// Redireciona para a página da vaga correspondente ao código
		return "redirect;/" + codigo;

	}

}
