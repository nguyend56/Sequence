package proj2;

/**
 * Model a Sequence which is a String array
 */
public class Sequence
{
    private String[] data;
    private int manyItems;
    private int currentIndex;

    private final int DEFAULT_CAPACITY = 10;

    /**
     * Creates a new sequence with initial capacity 10.
     */
    public Sequence() {
    	data = new String[DEFAULT_CAPACITY];
    	manyItems = 0;
    	currentIndex = manyItems;
    }


    /**
     * Creates a new sequence.
     * 
     * @param initialCapacity the initial capacity of the sequence.
     */
    public Sequence(int initialCapacity) {
    	if (initialCapacity < 0)
    	    throw new IllegalArgumentException("initialCapacity is negative: " + initialCapacity);
        data = new String[initialCapacity];
        manyItems = 0;
    	currentIndex = manyItems;
    }


    /**
     * Adds a string to the sequence in the location before the
     * current element. If the sequence has no current element, the
     * string is added to the beginning of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences' capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addBefore(String value) {
        expandSequence();
        if (isCurrent()) {
            moveRight(getCurrentIndex());
            getData()[getCurrentIndex()] = value;
            setManyItems(size() + 1);
        } else {
            moveRight(0);
            getData()[0] = value;
            setManyItems(size() + 1);
            setCurrentIndex(0);
        }
    }

    
    /**
     * Adds a string to the sequence in the location after the current
     * element. If the sequence has no current element, the string is
     * added to the end of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences' capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addAfter(String value)
    {
        expandSequence();
        if (isCurrent()){
            setCurrentIndex(getCurrentIndex() + 1);
            moveRight(getCurrentIndex());
            getData()[getCurrentIndex()] = value;
            setManyItems(size() + 1);
        }
        else {
            getData()[size()] = value;
            setCurrentIndex(size());
            setManyItems(size() + 1);
        }
    }

    
    /**
     * @return true if and only if the sequence has a current element.
     */
    public boolean isCurrent()
    {
        return ((0 <= getCurrentIndex()) && (getCurrentIndex() < size()));
    }
    
    
    /**
     * @return the capacity of the sequence.
     */
    public int getCapacity()
    {
        return getData().length;
    }

    
    /**
     * @return the element at the current location in the sequence, or
     * null if there is no current element.
     */
    public String getCurrent()
    {
        if (isCurrent()){
            return getData()[getCurrentIndex()];
        } else {
            return null;
        }
    }
    
    
    /**
     * Increase the sequence's capacity to be
     * at least minCapacity.  Does nothing
     * if current capacity is already >= minCapacity.
     *
     * @param minCapacity the minimum capacity that the sequence
     * should now have.
     */
    public void ensureCapacity(int minCapacity)
    {
        if (getCapacity() < minCapacity){
            setNewDataBasedOnCapacity(minCapacity);
        }
    }

    
    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * If adding all elements of the other sequence would exceed the
     * capacity of this sequence, the capacity is changed to make room for
     * all of the elements to be added.
     * 
     * Postcondition: NO SIDE EFFECTS!  the other sequence should be left
     * unchanged.  The current element of both sequences should remain
     * where they are. (When this method ends, the current element
     * should refer to the same element that it did at the time this method
     * started.)
     *
     * @param another the sequence whose contents should be added.
     */
    public void addAll(Sequence another)
    {
        if(size() + another.size() > getCapacity()){
            ensureCapacity(size() + another.size());
        }
        System.arraycopy(another.getData(), 0, getData(), size(), another.size());
        if (!isCurrent()){
            setCurrentIndex(size() + another.size());
        }
        setManyItems(size() + another.size());
    }


    /**
     * Move forward in the sequence so that the current element is now
     * the next element in the sequence.
     *
     * If the current element was already the end of the sequence,
     * then advancing causes there to be no current element.
     *
     * If there is no current element to begin with, do nothing.
     */
    public void advance()
    {
        if (isCurrent()) {
            setCurrentIndex(getCurrentIndex() + 1);
        }
    }


    /**
     * Make a copy of this sequence.  Subsequence changes to the copy
     * do not affect the current sequence, and vice versa.
     * 
     * Postcondition: NO SIDE EFFECTS!  This sequence's current
     * element should remain unchanged.  The clone's current
     * element will correspond to the same place as in the original.
     *
     * @return the copy of this sequence.
     */
    public Sequence clone()
    {
        Sequence answer = new Sequence(getCapacity());
        answer.addAll(this);
        answer.start();
        for (int i = 0; i < getCurrentIndex(); i++){
            answer.advance();
        }
        return answer;
    }
   
    
    /**
     * Remove the current element from this sequence.  The following
     * element, if there was one, becomes the current element.  If
     * there was no following element (current was at the end of the
     * sequence), the sequence now has no current element.
     *
     * If there is no current element, does nothing.
     */
    public void removeCurrent()
    {
        if (isCurrent()){
            if (getCurrentIndex()== size() - 1){
                getData()[getCurrentIndex()] = null;
                setCurrentIndex(size() + 1);
                setManyItems(size() - 1);
                return;
            }
            for (int i = getCurrentIndex(); i < size() - 1; i++){
                setDataElement(i, getDataElement(i + 1));
            }
            setManyItems(size() - 1);
        }
    }

    
    /**
     * @return the number of elements stored in the sequence.
     */
    public int size()
    {
        return manyItems;
    }

    
    /**
     * Sets the current element to the start of the sequence.  If the
     * sequence is empty, the sequence has no current element.
     */
    public void start()
    {
        if (size() > 0){
            setCurrentIndex(0);
        }
    }

    
    /**
     * Reduce the current capacity to its actual size, so that it has
     * capacity to store only the elements currently stored.
     */
    public void trimToSize()
    {
        if (size() < getData().length){
            setNewDataBasedOnCapacity(size());
        }
    }

    
    /**
     * Produce a string representation of this sequence.  The current
     * location is indicated by a >.  For example, a sequence with "A"
     * followed by "B", where "B" is the current element, and the
     * capacity is 5, would print as:
     * 
     *    {A, >B} (capacity = 5)
     * 
     * The string you create should be formatted like the above example,
     * with a comma following each element, no comma following the
     * last element, and all on a single line.  An empty sequence
     * should give back "{}" followed by its capacity.
     *
     * @return a string representation of this sequence.
     */
    public String toString()
    {
        String sequenceName = "{";
        for (int i = 0; i < size(); i++){
            if (getCurrentIndex() == i){
                sequenceName += ">";
            } sequenceName += getData()[i];
            if (i < size() - 1){
                sequenceName += ", ";
            }
        } sequenceName += "} (capacity = " + getCapacity() + ")";
        return sequenceName;
    }


    /**
     * Checks whether another sequence is equal to this one.  To be
     * considered equal, the other sequence must have the same size
     * as this sequence, have the same elements, in the same
     * order, and with the same element marked
     * current.  The capacity can differ.
     * 
     * Postcondition: NO SIDE EFFECTS!  this sequence and the
     * other sequence should remain unchanged, including the
     * current element.
     * 
     * @param other the other Sequence with which to compare
     * @return true iff the other sequence is equal to this one.
     */
    public boolean equals(Sequence other) {
        if((this.size() == other.size()) && (this.getCurrentIndex() == other.getCurrentIndex())){
            for (int i=0; i<this.size(); i++){
                if (!(this.getData()[i].equals(other.getData()[i]))){
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }
    
    
    /**
     * 
     * @return true if Sequence empty, else false
     */
    public boolean isEmpty()
    {
        return (size() == 0);
    }
    
    
    /**
     *  empty the sequence.  There should be no current element.
     */
    public void clear()
    {
        for (int i = 0; i < size(); i ++){
            setDataElement(i, null);
        }
        setManyItems(0);
        setCurrentIndex(0);
    }

// -------------------------------------------------------------------------------------------------------------------
// private methods

    /**
     * Getter of currentIndex
     * @return integer currentIndex
     */
    private int getCurrentIndex(){
        return currentIndex;
    }


    /**
     * Setter of currentIndex
     */
    private void setCurrentIndex(int newCurrentIndex){
        currentIndex = newCurrentIndex;
    }


    /**
     * Getter for data
     * @return String array data
     */
    private String[] getData(){
        return data;
    }


    /**
     * Setter for data
     * @param newData the new String array to update the original String array data
     */
    private void setData(String[] newData){
        data = newData;
    }


    /**
     * Getter for specific element in array data
     * @param index index of an element as integer
     * @return String: an element at the index
     */
    private String getDataElement(int index){
        return data[index];
    }

    /**
     * Setter for specific element in array data
     * @param index index of an element as integer
     * @param newElement new value set to the element
     */
    private void setDataElement(int index, String newElement){
        data[index] = newElement;
    }


    /**
     * Setter for manyItems
     * @param newManyItems the new value set to manyItems
     */
    private void setManyItems(int newManyItems){
        manyItems = newManyItems;
    }


    /**
     * Setter for data, update to a custom size of the array
     * @param newCapacity the size of updated array
     */
    private void setNewDataBasedOnCapacity(int newCapacity){
        String[] newData = new String[newCapacity];
        System.arraycopy(getData(), 0, newData, 0, size());
        setData(newData);
    }


    /**
     * the sequence expand to twice its current capacity plus 1 if the sequences' capacity has been reached
     */
    private void expandSequence(){
        if (size() == getData().length){
            ensureCapacity(size() * 2 + 1);
        }
    }


    /**
     * move to the right all elements from startIndex index to the last one
     * @param startIndex the index determine where to start moving right
     */
    private void moveRight(int startIndex){
        for (int i = manyItems; i > startIndex; i--){
            data[i] = data[i - 1];
        }
    }
}