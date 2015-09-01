/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma2gui;

import java.util.ArrayList;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author patlas
 */
public class Enigma2Gui {

    private JTree tree = null;
    private final DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Transponders" );
    
    public Enigma2Gui(JTree tree){
        this.tree = tree;
        
    }
    
    public void addTranspondersToTree(ArrayList<ArrayList<String>> transponders){
        
        DefaultMutableTreeNode transponder, parameters, channels = null;
        int index = 0;
        for(ArrayList<String> tr : transponders){
            
            transponder = new DefaultMutableTreeNode("Transponder "+ tr.get(0).toUpperCase() );
            parameters = new DefaultMutableTreeNode("Parameters");
            channels = new DefaultMutableTreeNode("Channels");
            root.add( transponder );
            transponder.add(parameters);
            transponder.add(channels);
            index = 0;
            
            for(String str : tr){
                
                switch(index){
                    case 0:
                        index++;
                        continue;
                        
                    case 1:
                        parameters.add(new DefaultMutableTreeNode("Frequency: "+str));
                        break;
                    case 2:
                        parameters.add(new DefaultMutableTreeNode("Bitrate: "+str+"[b/s]"));
                        break;
                    case 3:
                        parameters.add(new DefaultMutableTreeNode("Polarization: "+str));
                        break;
                    case 4:
                        parameters.add(new DefaultMutableTreeNode("FEC: "+str));
                        break;
                    case 5:
                        parameters.add(new DefaultMutableTreeNode("Position: "+str));
                        break;
                }
                index++;
            }        
        }
        
        tree.setModel(new DefaultTreeModel( root ));
        tree.setRootVisible(false);
        
        
        
        ///////////dwa poniższe są bardzo ważne
       // System.out.println(tree.getModel().getChild(root, 1).toString());
        
        //((DefaultMutableTreeNode)((DefaultMutableTreeNode)tree.getModel().getChild(root, 1)).getChildAt(1)).add(new DefaultMutableTreeNode("Frequency:"));
        ////////////
    }
    
    public void addChannellsToTransponders(ArrayList<ArrayList<String>> channels){
        
        for(ArrayList<String> ch : channels){
            
            int id = 0;
            long index=0;
            for(String str : ch){
                
                if(index==0){
                    id = findTransponderIndexById(str);
                    index++;
                    continue;
                }
                
                ((DefaultMutableTreeNode)((DefaultMutableTreeNode)tree.getModel().getChild(root,id )).getChildAt(1)).add(new DefaultMutableTreeNode(str));
                index++;
                if( index > 1) break;
            }
        }
        
    }
    
    
    private int findTransponderIndexById(String id){
        int index;
        String str = null;
        for(index=0; index<(tree.getModel().getChildCount(root)); index++){
            
            str = (tree.getModel().getChild(root, index).toString().split(" "))[1];
            
            if( str.equalsIgnoreCase(id)){
                return index;
            }
            
        }
        return index;
    }
}
