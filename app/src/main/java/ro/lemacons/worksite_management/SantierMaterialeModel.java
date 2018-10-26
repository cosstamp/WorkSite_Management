package ro.lemacons.worksite_management;

public class SantierMaterialeModel {

    private String nume_material;
    private String cantitate;
    private String um;

    public SantierMaterialeModel() {
    }

    public SantierMaterialeModel(String nume_material, String cantitate, String um) {
        this.nume_material = nume_material;
        this.cantitate = cantitate;
        this.um = um;
    }

    public String getNume_material() {
        return nume_material;
    }

    public void setNume_material(String nume_material) {
        this.nume_material = nume_material;
    }

    public String getCantitate() {
        return cantitate;
    }

    public void setCantitate(String cantitate) {
        this.cantitate = cantitate;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }
}
