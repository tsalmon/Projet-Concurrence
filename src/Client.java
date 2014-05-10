

class Client {
    public static void main(String[] args) {
        try {
            CharacterReseau cclient = CharacterReseau.getCharacter(args[0], null, null);
            cclient.run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
