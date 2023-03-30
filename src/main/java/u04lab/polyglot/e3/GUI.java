package u04lab.polyglot.e3;

import u04lab.polyglot.e3.Model.Cell;

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
    private final LogicsImpl2 logics;
    private final int size;
    
    public GUI(int size, int numberOfMines) {
        this.size = size;
        this.logics = new LogicsImpl2(size, numberOfMines);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);
        
        ActionListener onClick = (e)->{
            final JButton bt = (JButton)e.getSource();
            final Pair<Integer,Integer> pos = buttons.get(bt);
            boolean aMineWasFound = this.logics.hit(pos.getX(), pos.getY()); // call the logic here to tell it that cell at 'pos' has been seleced
            if (aMineWasFound) {
                quitGame();
                JOptionPane.showMessageDialog(this, "You lost!!");
            } else {
                this.buttons.entrySet().stream().filter(entry -> entry.getValue().equals(pos)).forEach(entry -> entry.getKey().setEnabled(false));
                drawBoard();
            }
            boolean isThereVictory = this.logics.won(); // call the logic here to ask if there is victory
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
                    if (logics.hasFlag(pos.getX(), pos.getY())) {
                        logics.removeFlag(pos.getX(), pos.getY());
                    } else {
                        logics.placeFlag(pos.getX(), pos.getY());
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
        this.logics.setVisibleMines();
        this.drawBoard();
    	for (var entry: this.buttons.entrySet()) {
            entry.getKey().setEnabled(false);
            Pair<Integer, Integer> pos = entry.getValue();
            if (this.logics.hasMine(pos.getX(), pos.getY()))
                entry.getKey().setText("*");
            }
    }

    private void drawBoard() {
        this.buttons.forEach((bt, pos) -> {
            if (this.logics.hasFlag(pos.getX(), pos.getY()) && bt.isEnabled()) {
                bt.setText("F");
            } else if (!this.logics.hasFlag(pos.getX(), pos.getY()) && bt.isEnabled()) {
                bt.setText(" ");
            } else if (!bt.isEnabled() && bt.getText().equals(" ")) {
                int numberOfAdjacentMines = 0;
                for (int i=0; i<3; i++) {
                    for (int j=0; j<3; j++) {
                        if (i==1 && j==1) continue;
                            int x = pos.getX() + i - 1;
                            int y = pos.getY() + j - 1;
                        if (this.logics.isAdjacentCellAMine(x, y)) {
                            numberOfAdjacentMines = numberOfAdjacentMines + 1;
                        }
                    }
                }
                bt.setText(String.valueOf(numberOfAdjacentMines));
                if (numberOfAdjacentMines == 0) {
                    for (int i=0; i<3; i++) {
                        for (int j=0; j<3; j++) {
                            int x = pos.getX() + i - 1;
                            int y = pos.getY() + j - 1;
                            if (x >= 0 && x < this.size  && y >= 0 && y < this.size) {
                                this.logics.hit(x, y);
                                this.buttons.entrySet().stream().filter(entry2 -> entry2.getValue().equals(new Pair<>(x, y))).forEach(entry2 -> entry2.getKey().setEnabled(false));
                            }
                        }
                    }
                    drawBoard();
                }
            }
        });
    }
}



