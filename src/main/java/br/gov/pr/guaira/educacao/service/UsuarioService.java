package br.gov.pr.guaira.educacao.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import br.gov.pr.guaira.educacao.exception.ImpossivelExcluirEntidadeException;
import br.gov.pr.guaira.educacao.model.Usuario;
import br.gov.pr.guaira.educacao.repository.Usuarios;

@Service
public class UsuarioService {

	@Autowired
	private Usuarios usuarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Usuario salvar(Usuario usuario) {
		
		Optional<Usuario> usuarioExistente = this.usuarios.findByEmailIgnoreCase(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario) && usuario.isNovo()) {
			throw new EmailUsuarioJaCadastrado("E-mail já cadastrado!");
		}
		
		if(usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
			throw new SenhaObrigatorioUsuarioException("Senha é obrigatória!");
		}
		
		if(usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));			
		}else if(StringUtils.isEmpty(usuario.getSenha())) {
			usuario.setSenha(usuarioExistente.get().getSenha());
		}
				
		usuario.setConfirmacaoSenha(usuario.getSenha());
		
		if(!usuario.isNovo() && usuario.getAtivo() == null) {
			usuario.setAtivo(usuarioExistente.get().getAtivo());
		}
			
		return usuarios.save(usuario);
	}

	@Transactional
	public void alterarStatus(Long[] codigos, StatusUsuario status) {
		status.executar(codigos, usuarios);		
	}

	@Transactional
	public void excluir(Usuario usuario) {
		
		try {
			this.usuarios.delete(usuario);
			this.usuarios.flush();
		}catch(PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Não foi possível excluir o usuário!"); 
		}
		
	}
}
