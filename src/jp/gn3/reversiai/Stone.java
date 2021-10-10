package jp.gn3.reversiai;

public enum Stone {
    none,
    white,
    black;

    public Stone getReverse(){
        if(this == none){
            return none;
        }
        else if(this == white){
            return black;
        }
        else if(this == black){
            return white;
        }
        return none;
    }
}
