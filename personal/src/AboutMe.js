import { Button} from "reactstrap";

function AboutMe(){
    return(
        <div className="bg-dark text-light d-grid justify-content-center">
            <h1 className="d-flex justify-content-center">About Me</h1>
            <p className="text-center">Stuff About Me</p>
            <Button>Download Resume</Button>
        </div>
    );
}

export default AboutMe;