package sga.core;

import java.util.List;

public interface LabDao {
    public void addLab(Lab lab);

    public Lab getLab(int id);

    public void removeLab(int id);

    public void updateLab(Lab lab);

    public List<Lab> getAllLabs();
}