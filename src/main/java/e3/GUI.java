package e2;

import e2.Model.Cell;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import java.io.Serial;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    
    @Serial
    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;
    
    public GUI(int size, int numberOfMines) {
        this.logics = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        this.logics.placeMines(numberOfMines);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener onClick = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> pos = buttons.get(bt);
            boolean aMineWasFound = this.logics.hit(pos); // call the logic here to tell it that cell at 'pos' has been seleced
            if (aMineWasFound) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You lost!!");
            } else {
                this.buttons.entrySet().stream().filter(entry -> entry.getValue().equals(pos)).forEach(entry -> entry.getKey().setEnabled(false));
                drawBoard();
            }
            boolean isThereVictory = this.logics.checkWin(); // call the logic here to ask if there is victory
            if (isThereVictory){
                quitGame();
                JOptionPane.showMessageDialog(this, "You won!!");
                System.exit(0);
            }
        };

        MouseInputListener onRightClick = new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final JButton bt = (JButton)e.getSource();
                if (bt.isEnabled()){
                    final Pair<Integer,Integer> pos = buttons.get(bt);
                    if (logics.hasFlag(pos)) {
                        logics.removeFlag(pos);
                    } else {
                        logics.placeFlag(pos);
                    }
                }
                drawBoard(); 
            }
        };
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(onClick);
                jb.addMouseListener(onRightClick);
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.drawBoard();
        this.setVisible(true);
    }
    
    private void quitGame() {
        this.drawBoard();
    	for (var entry: this.buttons.entrySet()) {
            entry.getKey().setEnabled(false);
            this.logics.getCellsWithMines().stream().filter(cell -> cell.getPosition().equals(entry.getValue())).forEach(cell -> entry.getKey().setText("*"));
    	}
    }

    private void drawBoard() {
        this.buttons.forEach((bt, pos) -> {
            if (this.logics.hasFlag(pos) && bt.isEnabled()) {
                bt.setText("F");
            } else if (!this.logics.hasFlag(pos) && bt.isEnabled()) {
                bt.setText(" ");
            } else if (!bt.isEnabled() && bt.getText().equals(" ")) {
                final int numberOfAdjacentMines = this.logics.getAdjacentCells(pos).stream().filter(Cell::hasMine).toArray().length;
                bt.setText(String.valueOf(numberOfAdjacentMines));
                if (numberOfAdjacentMines == 0) {
                    this.logics.getAdjacentCells(pos).forEach(cell -> {
                        this.logics.hit(cell.getPosition());
                        this.buttons.entrySet().stream().filter(entry2 -> entry2.getValue().equals(cell.getPosition())).forEach(entry2 -> entry2.getKey().setEnabled(false));
                    });
                    drawBoard();
                }
            }
        });
    }
}
