public class Model {

    private Viewer viewer;
    private String model;
    private boolean request;
    private Service service;

    public Model(Viewer viewer) {
        this.viewer = viewer;
        request = true;
        model = "";
        service = new Service();
    }

    public void doAction(String command) {
        if(command.equals("Clear")){
            model = "";
            request = true;
        } else if(command.equals("ToggleSign")){
            setToggleSign();
        } else  if(command.equals("Equal")){
            if(request){
                model = service.calculate(model);
                request = false;
            } else {
                model = "";
                request = true;
            }
        } else if(command.equals(".")) {
            if(model.isEmpty()) {
                model = "0.";
            } else {
                for(int i = model.length() - 1; i >= 0; i--){
                    if(model.charAt(i) == '.'){
                        break;
                    }
                    if(isOperator(model.charAt(i))){
                        if(model.length() - 1 == i){
                            model = model + "0.";
                        } else {
                            model = model + ".";
                        }
                        break;
                    }
                    if(i == 0){
                        model = model + ".";
                        break;
                    }
                }
            }
        } else {
            if(request){
                if(model.length() == 0 || !(isOperator(model.substring(model.length() - 1)) && isOperator(command))){
                    model = model + command;
                }
            } else {
                request = true;
                if(isOperator(command.charAt(0))){
                    model = model + command;
                } else {
                    model = command;
                }
            }
        }
        viewer.update(model);
    }
    private void setToggleSign(){
        for(int i = model.length() - 1; i >= 0; i--){
            if(isOperator(model.charAt(i))){
                if(i == model.length() - 1 && model.charAt(i) == ')'){
                    for(int j = i - 1; j >= 0; j--){
                        if(isDigit(model.charAt(j)) || model.charAt(j) == '.'){
                            continue;
                        }
                        if(model.charAt(j) == '('  && model.charAt(j + 1) == '-') {
                            model = model.substring(0, j) + model.substring(j + 2, model.length() - 1);
                            break;
                        } else if(isOperator(model.charAt(j)) && model.charAt(j) != '-' && model.charAt(j) != '('){
                            System.out.println("break " + model.charAt(j));
                            break;
                        }
                    }
                    break;
                }
                if(i == model.length() - 1){
                    break;
                }
                model = model.substring(0, i + 1) + "(-" + model.substring(i + 1) + ")";
                break;
            }
            if(i == 0){
                model = "(-" + model + ")";
            }
        }
    }
    private boolean isDigit(char c){
        return 48 <= c && c <= 58;
    }
    private boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }
    private boolean isOperator(String c){
        return c.equals("+") || c.equals("-") || c.equals("*") || c.equals("/");
    }
}
