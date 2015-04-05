package com.prueba.soccerscore.app;

/*
 * Created by David on 28/03/2015.
 */
public class Utility {

    public static int getEscudoParaVistaMatch(String team) {

        if (team.equals("Almería")) {
            return R.drawable.almeria_p;
        } else if (team.equals("Athletic")) {
            return R.drawable.at_bilbao_p;
        } else if (team.equals("Atlético")) {
            return R.drawable.at_madrid_p;
        } else if (team.equals("Barcelona")) {
            return R.drawable.barcelona_p;
        } else if (team.equals("Celta")) {
            return R.drawable.celta_p;
        } else if (team.equals("Córdoba")) {
            return R.drawable.cordoba_p;
        } else if (team.equals("Deportivo")) {
            return R.drawable.dep_coruna_p;
        } else if (team.equals("Eibar")) {
            return R.drawable.eibar_p;
        } else if (team.equals("Elche")) {
            return R.drawable.elche_p;
        } else if (team.equals("Espanyol")) {
            return R.drawable.espanyol_p;
        } else if (team.equals("Getafe")) {
            return R.drawable.getafe_p;
        } else if (team.equals("Granada")) {
            return R.drawable.granada_p;
        } else if (team.equals("Levante")) {
            return R.drawable.levante_p;
        } else if (team.equals("Málaga")) {
            return R.drawable.malaga_p;
        } else if (team.equals("Rayo Vallecano")) {
            return R.drawable.rayo_vallecano_p;
        } else if (team.equals("Real Madrid")) {
            return R.drawable.real_madrid_p;
        } else if (team.equals("R. Sociedad")) {
            return R.drawable.real_sociedad_p;
        } else if (team.equals("Sevilla")) {
            return R.drawable.sevilla_p;
        } else if (team.equals("Valencia")) {
            return R.drawable.valencia_p;
        } else if (team.equals("Villarreal")) {
            return R.drawable.villareal_p;
        }
        return -1;
    }

    public static int getEscudoParaVistaScore(String team) {

        if (team.equals("Almería")) {
            return R.drawable.almeria_g;
        } else if (team.equals("Athletic")) {
            return R.drawable.at_bilbao_g;
        } else if (team.equals("Atlético")) {
            return R.drawable.at_madrid_g;
        } else if (team.equals("Barcelona")) {
            return R.drawable.barcelona_g;
        } else if (team.equals("Celta")) {
            return R.drawable.celta_g;
        } else if (team.equals("Córdoba")) {
            return R.drawable.cordoba_g;
        } else if (team.equals("Deportivo")) {
            return R.drawable.dep_coruna_g;
        } else if (team.equals("Eibar")) {
            return R.drawable.eibar_g;
        } else if (team.equals("Elche")) {
            return R.drawable.elche_g;
        } else if (team.equals("Espanyol")) {
            return R.drawable.espanyol_g;
        } else if (team.equals("Getafe")) {
            return R.drawable.getafe_g;
        } else if (team.equals("Granada")) {
            return R.drawable.granada_g;
        } else if (team.equals("Levante")) {
            return R.drawable.levante_g;
        } else if (team.equals("Málaga")) {
            return R.drawable.malaga_g;
        } else if (team.equals("Rayo Vallecano")) {
            return R.drawable.rayo_vallecano_g;
        } else if (team.equals("Real Madrid")) {
            return R.drawable.real_madrid_g;
        } else if (team.equals("R. Sociedad")) {
            return R.drawable.real_sociedad_g;
        } else if (team.equals("Sevilla")) {
            return R.drawable.sevilla_g;
        } else if (team.equals("Valencia")) {
            return R.drawable.valencia_g;
        } else if (team.equals("Villarreal")) {
            return R.drawable.villareal_g;
        }
        return -1;
    }


}
