package br.com.yaw.ssjc.controller;

import br.com.yaw.ssjc.action.AbstractAction;
import br.com.yaw.ssjc.dao.MercadoriaDAO;
import br.com.yaw.ssjc.event.DeletarMercadoriaEvent;
import br.com.yaw.ssjc.event.IncluirMercadoriaEvent;
import br.com.yaw.ssjc.model.Mercadoria;
import br.com.yaw.ssjc.ui.IncluirMercadoriaFrame;

/**
 * Define a <code>Controller</code> responsável por gerir a tela de inclusão/edição de <code>Mercadoria</code>.
 * 
 * @see br.com.yaw.ssjc.controller.AbstractController
 * 
 * @author YaW Tecnologia
 */
public class IncluirMercadoriaController extends AbstractController {

	private IncluirMercadoriaFrame frame;
	
	public IncluirMercadoriaController(final ListaMercadoriaController parent) {
		super(parent);
		this.frame = new IncluirMercadoriaFrame();
		
		frame.addWindowListener(this);
		registerAction(frame.getCancelarButton(), new AbstractAction() {
			@Override
			public void action() {
				cleanUp();
			}
		});
		
		registerAction(frame.getSalvarButton(), new AbstractAction() {
			private Mercadoria m;
			
			@Override
			public void action() {
				Mercadoria m = frame.getMercadoria();
				MercadoriaDAO dao =  parent.getMercadoriaDAO();
				dao.save(m);
			}
			
			@Override
			public void posAction() {
				cleanUp();
				fireEvent(new IncluirMercadoriaEvent(m));
			}
		});
		
		registerAction(frame.getExcluirButton(), new AbstractAction() {
			Mercadoria m;
			@Override
			public void action() {
				Integer id = frame.getMercadoriaId();
				if (id != null) {
					m = parent.getMercadoriaDAO().findById(id);
					if (m != null) {
						parent.getMercadoriaDAO().remove(m);
					}
				}
			}
			
			@Override
			public void posAction() {
				cleanUp();
				fireEvent(new DeletarMercadoriaEvent(m));
			}
		});
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
	public void show(Mercadoria m) {
		frame.setMercadoria(m);
		show();
	}
	
	@Override
	protected void cleanUp() {
		super.cleanUp();
		frame.setVisible(false);
		frame.resetForm();
	}
	
}
