package controllers;

import java.util.List;

import models.Anunciante;
import models.Anuncio;
import models.dao.GenericDAO;
import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Controlador Principal do Sistema
 */
public class Application extends Controller {
	private static Form<Anuncio> anuncioForm = Form.form(Anuncio.class);
	private static final GenericDAO dao = new GenericDAO();

	public static Result index() {
		return redirect(routes.Application.anuncio());
	}

	public static Result criarAnuncio() {
		return redirect(routes.Application.criar());
	}

	@Transactional
	public static Result criar() {
		// Todos os Livros do Banco de Dados
		List<Anuncio> result = dao.findAllByClass(Anuncio.class);
		return ok(views.html.criar.render(result));
	}

	/*
	 * A Anotação transactional é necessária em todas as Actions que usarem o
	 * BD.
	 */
	@Transactional
	public static Result anuncio() {
		// Todos os Livros do Banco de Dados
		List<Anuncio> result = dao.findAllByClass(Anuncio.class);
		return ok(views.html.index.render(result));
	}

	@Transactional
	public static Result newAnuncio() {
		// O formulário dos Livros Preenchidos
		Form<Anuncio> filledForm = anuncioForm.bindFromRequest();

		if (filledForm.hasErrors()) {
			List<Anuncio> result = dao.findAllByClass(Anuncio.class);
			// TODO falta colocar na interface mensagem de erro.
			return badRequest(views.html.index.render(result));
		} else {
			Anuncio novoAnuncio = filledForm.get();
			Logger.debug("Criando livro: " + filledForm.data().toString()
					+ " como " + novoAnuncio.getNome());
			// Persiste o Livro criado
			dao.persist(novoAnuncio);
			// Espelha no Banco de Dados
			dao.flush();
			/*
			 * Usar routes.Application.<uma action> é uma forma de evitar
			 * colocar rotas literais (ex: "/books") hard-coded no código. Dessa
			 * forma, se mudamos no arquivo routes, continua funcionando.
			 */
			return redirect(routes.Application.anuncio());
		}
	}

	@Transactional
	public static Result addAnunciante(Long id, String nome) {
		criaAnunciante(id, nome);
		return redirect(routes.Application.anuncio());
	}

	private static void criaAnunciante(Long id, String nome) {
		// Cria um novo Autor para um livro de {@code id}
		Anunciante anunciante = new Anunciante(nome);
		// Procura um objeto da classe Livro com o {@code id}
		Anuncio anuncio = dao.findByEntityId(Anuncio.class, id);
		// Faz o direcionamento de cada um
		anuncio.addAnunciante(anunciante);
		anunciante.addAnuncio(anuncio);
		// Persiste o Novo Autor
		dao.persist(anunciante);

		/*
		 * As informações do livro já serão automaticamente atualizadas no BD no
		 * final da transação. Isso porque o livro já existe no BD, e então já é
		 * gerenciado por ele.
		 * 
		 * Assim fica opcional fazer dao.merge(livroDaListagem);
		 */
		// Espelha no Banco de Dados
		dao.flush();
	}

	// Notação transactional sempre que o método fizer transação com o Banco de
	// Dados.
	@Transactional
	public static Result deleteAnuncio(Long id) {
		// Remove o Livro pelo Id
		dao.removeById(Anuncio.class, id);
		// Espelha no banco de dados
		dao.flush();
		return redirect(routes.Application.anuncio());
	}

}
