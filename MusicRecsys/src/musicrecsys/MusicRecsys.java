/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package musicrecsys;

import model.UtilityMatrix;

/**
 *
 * @author Lester Chong
 */
public class MusicRecsys {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        UtilityMatrix um = new UtilityMatrix();
        
        for(int ctr =0; ctr<3; ctr++){
            System.out.println(um.topThreeUtility().get(ctr).getName());
        }
    }
}