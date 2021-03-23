var LambariusPro = LambariusPro || {}

LambariusPro.MaskCpf = (function () {
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
      $('.js-documento-cpf').on('blur', validaCPF.bind(this))
    }
  }

  return MaskCpf
}())

 function validaCPF() {
	 var valor = $('.js-documento-cpf').val().length
	    if (valor < 14) {
	      $('.js-documento-cpf').val('')

	    } 
  }

LambariusPro.MaskCpfCnpj = (function () {

  class MaskCpfCnpj {
    constructor() {
    }
    enable() {
      var options = {
        onKeyPress: function (cpfcnpj, e, field, options) {
          var masks = ['000.000.000-009', '00.000.000/0000-00']
          var mask = (cpfcnpj.length > 14) ? masks[1] : masks[0]
          $('.js-documento').mask(mask, options)
        }
      }

      $('.js-documento').mask('000.000.000-009', options)

      $('.js-documento').on('blur', validaDocumento.bind(this))
    }
  }


  function validaDocumento() {
    var valor = $('.js-documento').val().length
    if (valor < 14) {
      $('.js-documento').val('')

    } else if (valor > 14 && valor < 18) {
      $('.js-documento').val('')
    }
  }

  return MaskCpfCnpj

}())

LambariusPro.MaskPhone = (function () {

  class MaskPhone {
    constructor() {
      this.inputPhone = $('.js-phone')
      this.inputCellPhone = $('.js-cell-phone')
    }
    enable() {
      var maskBehavior = function (val) {
        return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009'
      }

      var options = {
        onKeyPress: function (val, e, field, options) {
          field.mask(maskBehavior.apply({}, arguments), options)
        }
      }

      this.inputPhone.mask(maskBehavior, options)
      this.inputCellPhone.mask(maskBehavior, options)

      this.inputPhone.on('blur', validaPhone.bind(this))
      this.inputCellPhone.on('blur', validaCellPhone.bind(this))
    }
  }

  function validaPhone() {
    if (this.inputPhone.val().length < 14) {
      this.inputPhone.val('')
    }
  }

  function validaCellPhone() {
    if (this.inputCellPhone.val().length < 15) {
      this.inputCellPhone.val('')
    }
  }

  return MaskPhone

}())


$(() => {
  var maskCpf = new LambariusPro.MaskCpf()
  maskCpf.enable()

  var maskCpfCnpj = new LambariusPro.MaskCpfCnpj()
  maskCpfCnpj.enable()

  var maskPhone = new LambariusPro.MaskPhone()
  maskPhone.enable()
  
})
$(function(){	
	
	$('.select2bs4').select2({
		theme : 'bootstrap4'
	});
	
	$(document).ready(function() {
		$('.select3').select2();
	});

	
	
});

