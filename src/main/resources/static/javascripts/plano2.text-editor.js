var Plano = Plano || {}

Plano.EditorTexto = (function(){
	
	function EditorTexto(){
		this.btnSubmit = $('.js-btn-submit');
		this.fieldDescricao = $('input[name=descricao]');
		this.fieldDescricaoHtml = $('input[name=descricaoHtml]');		
		this.theme = 'snow';
		this.editorDescricao = '.desc';		
		this.optionsFullTollbar = [
	        [{ 'font': [] }, { 'size': [] }],
	        [ 'bold', 'italic', 'underline', 'strike' ],
	        [{ 'color': [] }, { 'background': [] }],
	        [{ 'script': 'super' }, { 'script': 'sub' }],
	        [{ 'header': '1' }, { 'header': '2' }, 'blockquote', 'code-block' ],
	        [{ 'list': 'ordered' }, { 'list': 'bullet'}, { 'indent': '-1' }, { 'indent': '+1' }],
	        [ {'direction': 'rtl'}, { 'align': [] }],
	        [ 'link', 'image', 'video', 'formula' ],
	        [ 'clean' ]
	      ];
		
		this.optionsTollbar = [
	        [{ 'font': [] }, { 'size': [] }],
	        [ 'bold', 'italic', 'underline', 'strike' ],
	        [{ 'color': [] }, { 'background': [] }],
	        [{ 'script': 'super' }, { 'script': 'sub' }],
	        [{ 'header': '1' }, { 'header': '2' }, 'blockquote'],
	        [{ 'list': 'ordered' }, { 'list': 'bullet'}, { 'indent': '-1' }, { 'indent': '+1' }],
	        [ {'direction': 'rtl'}, { 'align': [] }],
	        [ 'link'],
	        [ 'clean' ]
	      ];
	}
	
	EditorTexto.prototype.enable = function(){
		var fullEditor = new Quill(this.editorDescricao, {
		    bounds: this.editorDescricao,
		    modules: {
		      syntax: true,
		      toolbar: this.optionsTollbar,
		    },
		    theme: this.theme
		  });		
		
		
		if(this.fieldDescricao.val()){
			fullEditor.setContents(JSON.parse(this.fieldDescricao.val()), 'api');
		}	
		
		this.btnSubmit.on('click', putText.bind(this, fullEditor));
	}
	
	function putText(text){
		
		this.fieldDescricao.val(JSON.stringify(text.getContents()));		
		
		this.fieldDescricaoHtml.val(text.container.firstChild.innerHTML);
	
		
	}
	
	return EditorTexto;
}());

$(function(){
	
	var editorTexto = new Plano.EditorTexto();
	editorTexto.enable();
	
});