angular.module("conpag").factory("utils", function($http, $window){

	return{
		dateUtils:{
        	getInicioAno: function(){
        		var inicioAno = new Date();
        		var ano = inicioAno.getFullYear();
        		inicioAno.setFullYear(ano, 0, 1);
            	
        		return inicioAno;
        	},
        	getInicioMes: function(){
        		var data = new Date();
        		var mes = data.getMonth();
        		var ano = data.getFullYear();
        		data.setFullYear(ano, mes, 1);
            	
        		return data;
        		
        	},
        	getInicioProxMes: function(){
        		var proxMes = new Date();

        		var mes = proxMes.getMonth();
        		var ano = proxMes.getFullYear();
        		
        		if( mes == 12 ) ano ++;
        		mes = mes +1;

        		proxMes.setFullYear(ano, mes, 1);
        		
        		return proxMes;
        	}
        },
        formatUtils:{
        	mascaraFormat : function (valor, mascara){
        		
        		var valorFormatado = "";
        		var val = "";
        		var mas = "";
        		var v = 0; 
        		
        		var valid = mascara.split("#");
        		
        		if(valor){
        			
        			for(i=0; i <= valor.length; i++){
        				val = valor.substr(v,1);
        				mas = mascara.substr(i,1);
        				
        				if(mas == "#"){
        					valorFormatado = valorFormatado + val; //Adiciona valor
        				}else{
        					valorFormatado = valorFormatado + mas; //Adiciona máscara
        				}
        				v++;
        			}
        		}
        		
        		return valorFormatado;
        	},
        	calcularIdadeCompleta : function (dtNascimento){
        		
        		var hoje = new Date();
        		var niver = dtNascimento;
        		
        		var idade;
        		var dias;
        		var meses;
        		var anos;
        		var diasAux;
        		
        		if((hoje.getMonth() == 0) || (hoje.getMonth() == 2) || (hoje.getMonth() == 4) || (hoje.getMonth() == 6) || (hoje.getMonth() == 7) || (hoje.getMonth() == 9) || (hoje.getMonth() == 11)){
        			diasAux = 31;
        		}else if((hoje.getMonth() == 3) || (hoje.getMonth() == 5) || (hoje.getMonth() == 8) || (hoje.getMonth() == 10)){
        			diasAux = 30;
        		}else if((hoje.getMonth() == 1)){
        			diasAux = 28;
        		}
        		
        		// Já fez aniversário
        		if (hoje.getMonth() > niver.getMonth())
        		{
        			anos = hoje.getFullYear() - niver.getFullYear();
        			
        			if (hoje.getDate() < niver.getDate())
        			{
        				/* remove 1 mês, porque no mês corrente ainda
        				não ultrapassou o dia da data de aniversário */
        				meses = hoje.getMonth() - niver.getMonth() - 1;
        				
        				// a soma dos dias ultrapassados após o dia da data de aniversário
        				dias = hoje.getDate() + (diasAux - niver.getDate());
        			}
        			else
        			{
        				meses = hoje.getMonth() - niver.getMonth();
        				dias = hoje.getDate() - niver.getDate();
        			}
        		}
        		else if (hoje.getMonth() < niver.getMonth())
        		{
        			// remove 1 ano porque ainda não fez aniversário
        			anos = hoje.getFullYear() - niver.getFullYear() - 1;
        			
        			if (hoje.getDate() < niver.getDate())
        			{
        				meses = (12 - niver.getMonth()) + hoje.getMonth() -1;
        				
        				// a soma dos dias ultrapassados após o dia da data de aniversário
        				dias = hoje.getDate() + (diasAux - niver.getDate());
        			}
        			else
        			{
        				// adiciona 1 mês porque já passou do dia da data de aniversário
        				meses = (12 - niver.getMonth()) + hoje.getMonth();
        				dias = hoje.getDate() - niver.getDate();
        			}
        		}
        		else if (hoje.getMonth() == niver.getMonth())
        		{
        			if (hoje.getDate() < niver.getDate())
        			{
        				// remove 1 ano porque ainda não fez aniversário
        				anos = hoje.getFullYear() - niver.getFullYear() - 1;
        				meses = 12 - hoje.getMonth() + 1;
        				// a soma dos dias ultrapassados após o dia da data de aniversário
        				dias = hoje.getDate() + (diasAux - niver.getDate());
        			}
        			else
        			{
        				anos = hoje.getFullYear() - niver.getFullYear();
        				meses = hoje.getMonth() - niver.getMonth();
        				dias = hoje.getDate() - niver.getDate();
        			}
        		}
        		
        		return  anos + " ano(s), " + meses + " mês(es) e " + dias + " dia(s) ";
        		
        	}
        	
        }
	}
	
});