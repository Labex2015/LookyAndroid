package labex.feevale.br.looky.model;

import java.util.List;


/**
 * Created by pablo on 7/21/15.
 */
public class SourceAreas {

    public List<Area> areas;
    public List<Subject> subjects;

    public SourceAreas(){}

    public SourceAreas(List<Area> areas, List<Subject> subjects){
        this.areas = areas;
        this.subjects = subjects;
    }
}
