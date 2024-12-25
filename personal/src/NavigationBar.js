import { Link } from "react-router-dom";
import { Button, Navbar, NavbarText } from "reactstrap";

function NavigationBar(){
    return(
        <Navbar className='d-flex bg-black'>
          <Button color='light' className="navButton"><Link to='/' className='text-dark text-decoration-none'>Home</Link></Button>
          <NavbarText className="text-light"><p>Rochester,NY | Full-Stack Developer</p></NavbarText>
          <Button color="light" className="navButton"><Link to='/contact' className='text-dark text-decoration-none'>Contact</Link></Button>
        </Navbar>
    );
}

export default NavigationBar;