package labex.feevale.br.looky.utils.mocks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import labex.feevale.br.looky.model.Evaluation;
import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.utils.DateUtils;

/**
 * Created by 0126128 on 12/06/2015.
 */
public class ApplicationMocks {

    public static User frodoBolseiro;
    public static User samwiseGamgee;
    public static User pippin;

    public static User aragorn;

    public static Collection<? extends Evaluation> getEvaluations(Long idUser) {
        List list = new ArrayList();
        list.add(new Evaluation(1L, 5, 4,"Salvou minha pele, mas me deixava com fome.",
                DateUtils.stringToDate("01/12/2014"), loadPippin(++idUser)));

        list.add(new Evaluation(2L, 5, 4,"Salvou minha pele, mas me deixava com fome.",
                DateUtils.stringToDate("01/12/2014"), loadSamwise(++idUser)));

        list.add(new Evaluation(3L, 5, 5,"Salvou minha pele de um Nazgul e salvou o mundo.",
                DateUtils.stringToDate("01/12/2014"), loadFrodo(++idUser)));

        return list;
    }

    public static User loadSamwise(Long idUser){
        if(samwiseGamgee == null){
            samwiseGamgee = new User();
            samwiseGamgee.setId(idUser);
            samwiseGamgee.setUsername("Sam");
            samwiseGamgee.setName("Samwise Gamgee");
            samwiseGamgee.setPicture(null);//TODO: carregar fotos
            samwiseGamgee.setDegree("Botanica");
            samwiseGamgee.setSemester(8);
        }
        return samwiseGamgee;
    }

    public static User loadFrodo(Long idUser){
        if(frodoBolseiro == null){
            frodoBolseiro = new User();
            frodoBolseiro.setId(idUser);
            frodoBolseiro.setUsername("Sr.Monteiro");
            frodoBolseiro.setName("Frodo Bolseiro");
            frodoBolseiro.setPicture(null);//TODO: carregar fotos
            frodoBolseiro.setDegree("Administracao");
            frodoBolseiro.setSemester(8);
        }
        return frodoBolseiro;
    }

    public static User loadPippin(Long idUser){
        if(pippin == null){
            pippin = new User();
            pippin.setId(idUser);
            pippin.setUsername("Pippin");
            pippin.setName("Peregrin Took");
            pippin.setPicture(null);//TODO: carregar fotos
            pippin.setDegree("Relacoes Publicas");
            pippin.setSemester(9);
        }
        return pippin;
    }

    public static User mainUser(){
        if(aragorn == null){
            pippin = new User();
            pippin.setId(1L);
            pippin.setUsername("passoLargo");
            pippin.setName("Aragorn, filho de Arathorn");
            pippin.setPicture(null);//TODO: carregar fotos
            pippin.setDegree("Ciencia da Computacao");
            pippin.setSemester(9);
        }
        return aragorn;
    }
}
