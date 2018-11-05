package ro.lemacons.worksite_management;

public class SantierMaterialeModel {

    private String nume_material;
    private Integer cantitate;
    private Integer cantitate_necesara;
    private String um;

    public SantierMaterialeModel() {
    }


    public SantierMaterialeModel(String nume_material, Integer cantitate, Integer cant_nec, String um) {
        this.nume_material = nume_material;
        this.cantitate = cantitate;
        this.cantitate_necesara = cant_nec;
        this.um = um;
    }

    public String getNume_material() {
        return nume_material;
    }

    public void setNume_material(String nume_material) {
        this.nume_material = nume_material;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public Integer getCantitate_necesara() {
        return cantitate_necesara;
    }

    public void setCantitate_necesara(Integer cantitate_necesara) {
        this.cantitate_necesara = cantitate_necesara;
    }
}
