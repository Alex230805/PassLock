public class cella {
    private String nome;
    private byte[] EncryptedPassword;

    public cella(String nome, byte[] EncryptedPassword){
        this.setNome(nome);
        this.setEncryptedPassword(EncryptedPassword);
    }

    public String getNome() {
        return nome;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getEncryptedPassword() {
        return EncryptedPassword;
    }

    private void setEncryptedPassword(byte[] encryptedPassword) {
        EncryptedPassword = encryptedPassword;
    }
    @Override

    public String toString(){
        return "Nome: "+nome+"Password Oscurata: "+EncryptedPassword+"\n";
    }
}
