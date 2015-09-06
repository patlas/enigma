/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agh.project.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;


/**
 *
 * @author patlas
 */
public class LameBDparser {
    final static Logger logger = Logger.getLogger(LameBDparser.class.getName());
    File fd = null;
    
    public LameBDparser(String fName){
        fd = new File(fName);
  
    }
 
    public ArrayList<ArrayList<String>> getTransponders(){
        int flag = 0;
        byte index = 0;
        String transponderID = null;
        
        logger.debug("Parsing transponders info");
        
        ArrayList<ArrayList<String>> transponders = new ArrayList<ArrayList<String>>();
        ArrayList<String> settings = null;
        try{
    
            BufferedReader br = new BufferedReader(new FileReader(fd));
            String line;
            while ((line = br.readLine()) != null) {
               line = line.replaceAll("\t", "");
               if(flag==0){
                   System.out.println(line);
                   if(line.equalsIgnoreCase("transponders")){
                       flag=1;
                       continue;
                   }
               }
               else if(line.equalsIgnoreCase("end")){
                   return transponders;
               }
                else{
                    switch(index){
                        case 0:
                            settings = new ArrayList<String>();
                            //System.out.println(line.split(":").length);
                            transponderID = line.split(":")[1];
                            settings.add(transponderID);
                        break;
                        case 1:
                            line = line.split(" ")[1];
                            String[] sets = line.split(":");

                            settings.add(sets[0]);
                            settings.add(sets[1]);

                            switch(Integer.parseInt(sets[2])){
                                case 0:
                                    settings.add("H");
                                    break;
                                case 1:
                                    settings.add("V");
                                    break;
                                case 2:
                                    settings.add("Circular Left");
                                    break;
                                case 3:
                                    settings.add("Circular Right");
                                    break;
                            }

                            switch(Integer.parseInt(sets[3])){
                                case 0:
                                    settings.add("none");
                                    break;
                                case 1:
                                    settings.add("auto");
                                    break;
                                case 2:
                                    settings.add("1/2");
                                    break;
                                case 3:
                                    settings.add("2/3");
                                    break;
                                case 4:
                                    settings.add("3/4");
                                    break;
                                case 5:
                                    settings.add("5/6");
                                    break;
                                case 6:
                                    settings.add("7/8");
                                    break;
                                case 7:
                                    settings.add("3/5");
                                    break;
                                case 8:
                                    settings.add("4/5");
                                    break;
                                case 9:
                                    settings.add("8/9");
                                    break;
                                case 10:
                                    settings.add("9/10");
                                    break;
                            }

                            int pos = Integer.parseInt(sets[4]);
                            pos = pos / 10;
                            if(pos>0){
                                settings.add(""+pos+"E");
                            }
                            else{
                                settings.add(""+pos+"W");
                            }

                        break;

                        case 2:
                            transponders.add(settings);
                            index=0;
                            continue;

                    }
                    index++;

                }
               
            }
        } catch(IOException ioe){
            
        }
    return transponders;
}
    
public ArrayList<ArrayList<String>> getChannels(){
        int flag = 0;
        byte index = 0;
        String transponderID = null;
        
        logger.debug("Parsing channel list");
        
        ArrayList<ArrayList<String>> channels = new ArrayList<ArrayList<String>>();
        ArrayList<String> settings = null;
        try{
    
            BufferedReader br = new BufferedReader(new FileReader(fd));
            String line;
            while ((line = br.readLine()) != null) {
               line = line.replaceAll("\t", "");
               if(flag==0){
                   //System.out.println(line);
                   if(line.equalsIgnoreCase("services")){
                       flag=1;
                       continue;
                   }
               }
               else if(line.equalsIgnoreCase("end")){
                   return channels;
               }
                else{
                    switch(index){
                        case 0:
                            settings = new ArrayList<String>();
                            //System.out.println(line.split(":").length);
                            transponderID = line.split(":")[2];
                            settings.add(transponderID);
                        break;
                        case 1:
                            settings.add(line);
                        break;

                        case 2:
                            line = line.split(",")[0];
                            String[] lines = line.split(":");
                            if(lines.length > 1){
                                settings.add(lines[1]);
                            }
                            else{
                                settings.add(" ");
                            }
                            channels.add(settings);
                            index=0;
                            continue;

                    }
                    index++;

                }
               
            }
        } catch(IOException ioe){
            System.out.println(channels.size());
        }
    return channels;
}
    
    
}
