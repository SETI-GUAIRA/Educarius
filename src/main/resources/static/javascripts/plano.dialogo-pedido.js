Plano = Plano || {};

Plano.DialogoPedido = (function(){
	
	function DialogoPedido(){
		this.pedidoBtn = $('.js-pedido-btn');
	}
	
	DialogoPedido.prototype.iniciar = function(){
		this.pedidoBtn.on('click', onPedidoClicado.bind(this));
		
		if(window.location.search.indexOf('adicionado') > -1){
			swal('Pronto!', 'Lançando com sucesso!','success');
		}
	}
	
	function onPedidoClicado(event){
		event.preventDefault();
		var botaoClicado = $(event.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		
		swal({
			title: 'Efetuar Pedido?',
			text: 'Sera lançando o pedido do mês ' + objeto,
			showCancelButton: true,
			confirmButtonColor: '#009000',
			confirmBurronText: 'Sim, efetuar agora!',
			closeOnConfirm: false
		}, onPedidoConfirmado.bind(this, url));		
	}
	
	function onPedidoConfirmado(url){
		$.ajax({
			url: url,
			method: 'GET',
			success: onPedidoSucesso.bind(this),
			error: onErroPedido.bind(this)
		});
	}
	
	function onPedidoSucesso(){
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('adicionado') > -1 ? urlAtual : urlAtual + separador + 'adicionado';
		
		window.location = novaUrl;
	}
	
	function onErroPedido(e){
		swal('Oops!', e.responseText, 'error');
	}
	
	return DialogoPedido;
	
}());

$(function(){
	var dialogo = new Plano.DialogoPedido();
	dialogo.iniciar();
});