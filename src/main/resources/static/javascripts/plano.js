var Plano = Plano || {};

Plano.MaskCpf = (function () {
	  class MaskCpf {
	    constructor() {
	    }
	    enable() {
	      var options = {
	        onKeyPress: function (cpfcnpj, e, field, options) {
	          var mask = '000.000.000-09'
	          $('.js-documento-cpf').mask(mask, options)
	        }
	      }

	      $('.js-documento-cpf').mask('000.000.000-09', options)

	    }
	  }

	  return MaskCpf
	}())

Plano.MaskCep = (function() {
	
	function MaskCep() {
		this.inputCep = $('.js-cep');
	}
	
	MaskCep.prototype.enable = function() {
		this.inputCep.mask('00.000-000');
	}
	
	return MaskCep;
	
}());

Plano.MaskPhoneNumber = (function(){
	
	function MaskPhoneNumber(){
		this.inputPhone = $('.js-phone-number');
		this.inputCelPhone = $('.js-cel-number');
	}
	
	MaskPhoneNumber.prototype.enable = function(){
		this.inputPhone.mask('(00) 0000-0000');
		this.inputCelPhone.mask('(00) 00000-0000');
	}
	
	return MaskPhoneNumber;
	
}());

Plano.MaskDate = (function() {
	
	function MaskDate() {
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		});
	}
	
	return MaskDate;
	
}());

Plano.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());

$(function(){
	var MaskCpf = new Plano.MaskCpf();
	MaskCpf.enable();
	
	var maskCep = new Plano.MaskCep();
	maskCep.enable();
	
	var maskPhoneNumber = new Plano.MaskPhoneNumber();
	maskPhoneNumber.enable();
	
	var maskDate = new Plano.MaskDate();
	maskDate.enable();
	
});