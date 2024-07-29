package view;

import algorithm.IAlgorithm;

public interface IView {
    // At the start
    public void retrieveAlgorithm(IAlgorithm algorithm);
    public void createAndShowGUI();
}
