package eg.edu.alexu.csd.oop.draw.cs19;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {

	private List<Memento> mementoListUndo = new ArrayList<>();
	private List<Memento> mementoListRedo = new ArrayList<>();

	/**
	 * adds a state to the undo list.
	 * @param state ... the state to be added to the undo
	 */
	public void add(Memento state) {
		mementoListUndo.add(state);
	}
	
	/**
	 * adds a state to the redo list.
	 * @param state ... the state to be added to the redo.
	 */
	public void addRedo(Memento state) {
		mementoListRedo.add(state);
	} 

	/**
	 * gets the state of a certain index in the undo list.
	 * @param index ... the index of the required state.
	 * @return ... it returns the required state.
	 */
	public Memento getUndo(int index) {
		Memento temp = mementoListUndo.remove(index + 1);
		mementoListRedo.add(temp);
		return mementoListUndo.get(index);
	}

	/**
	 * gets the state of a certain index in the redo list.
	 * @param index ... the index of the required state.
	 * @return ... it returns the required state.
	 */
	public Memento getRedo(int index) {
		Memento temp = mementoListRedo.remove(index);
		mementoListUndo.add(temp);
		return temp;
	}

	/**
	 * gets the size of the undo list.
	 * @return ... it returns the size of the undo list.
	 */
	public int getSizeUndo() {
		return mementoListUndo.size();
	}
	
	/**
	 * gets the size of the redo list.
	 * @return ... it returns the size of the redo list.
	 */
	public int getSizeRedo() {
		return mementoListRedo.size();
	}
	
	/**
	 * it's used to clear the list of the redo list.
	 */
	public void clearRedo() {
		mementoListRedo.clear();
	}

}
