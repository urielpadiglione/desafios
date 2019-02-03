package com.urielpadiglione.stringparser.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StringParseService {
	Logger logger = LoggerFactory.getLogger(StringParseService.class);
	
	public String parseString(String texto, int lineLength) throws IOException {
		if(texto==null || texto.equals("")){
			logger.error("O campo texto não pode ser nulo ou estar em branco");
			return null;
		}
		if(lineLength<=0) {
			logger.error("O campo length deve ser maior que 0");
			return null;
		}
		
		//Jeito easy, sem justify:
 		String wrappedString = WordUtils.wrap(texto, lineLength);
 		
 		//jeito hard, com justify
 		String justified = this.justifyText(wrappedString, lineLength);
 		
 		//salva o output onde o projeto está sendo rodado
 		Path currentRelativePath = Paths.get("");
 		Files.write(Paths.get(currentRelativePath.toAbsolutePath().toString()+"\\output.txt"), justified.getBytes(), StandardOpenOption.WRITE, StandardOpenOption.CREATE,StandardOpenOption.APPEND);

 		return justified;
	}
	
	private String justifyText(String wrappedString, int lineLenght) {
		//regex para encontrar cada linha linha por quebra \n
		String[] lines = wrappedString.split("\r\n|\r|\n");
		logger.info(lines.toString());

		String justified ="";
		
		//varrendo cada linha pra montar o texto justified
		for(String line : lines) {
			if(line.length()==lineLenght)
				justified+=line+"\n";
			
			else if(line.length() < lineLenght) {
				String justifiedLine="";
				
				//calcula o tamanho faltante da linha pra chegar ao tamanho desejado.
				int numberOfSpaces = lineLenght-line.length();
				
				//conta quantos espaços há entre as palavras da linha
				int gaps=0;
				for(int i = 0;i < line.length();i++) {
					if(line.charAt(i)==' ') {
						gaps++;
					}
				}
				
				int numberOfSpacesToAdd=0;
				int remaining=0;
				
				if(gaps>0) {
					//numero de espaços para adicionar
					numberOfSpacesToAdd = numberOfSpaces/gaps;				
					//restante
					remaining = numberOfSpaces % gaps;
				}
				
				//array que vai guardar a quantidade certa de espaços entre cada palavra
				int[] spaces = new int[gaps+1]; // <--- o +1 é para manter o lenght desse array igual ao length do array "words" abaixo.
				
			    for (int i = 1; i <= gaps; i++)
			    	{	//retorna 1 se a quantidade for menor ou igual a quantidade restante
			            int extra = (i <= remaining) ? 1:0; 
			            
			            //adiciona a quantidade certa de espaço entre as palavras.
			            spaces[i-1]=numberOfSpacesToAdd + extra;
			    	}
			    
			    //separa a linha por palavras  /// onde a magia acontece (⋆._.)⊃▁⛥⌒*ﾟ.❉・゜・。.
			    String[] words = line.split(" ");
			    for(int i = 0;i < words.length; i++) {//<-- aqui optei por usar o 'for' comum ao invés do "String str : words" pois precisarei modificar o conteudo do array de string
			    	words[i] = words[i]+" "+quantidadeDeEspaco(spaces[i]);//<--- o espaço em branco no meio é necessário pois o "line.split" remove os espaços todos das palavras.
			    	justifiedLine +=words[i];
			    	//logger.info(justifiedLine);
			    }
			    justified+=justifiedLine+"\n";
			}
		}
		logger.info("TEXTO WRAPPED: \n"+ wrappedString+"\n");
		logger.info("TEXTO JUSTIFICADO: \n"+justified+"\n");
		return justified;
	}

	//esse metodo me auxilia a retornar a quantidade certa de espaços, dado uma quantidade int
	public String quantidadeDeEspaco(int quantidade) {
		String espacos="";
		for(int i = 0;i < quantidade; i++) {
			espacos+=" ";
		}
		
		return espacos;
	}
}
