import { Link } from "react-router-dom";
import { Button, Navbar, NavbarText } from "reactstrap";

function NavigationBar(){
    return(
        <Navbar color='dark' className='d-flex'>
          <Button color='light' className='navSpacing'><Link to='/' className='text-dark text-decoration-none'>Home</Link></Button>
          <NavbarText className='text-light navSpacing'>Conner DeFeo | Rochester,NY | Full-Stack Developer</NavbarText>
          <Button className='bg-light'><Link to='/contact' className='text-dark text-decoration-none'>Contact</Link></Button>
        </Navbar>
    );
}

export default NavigationBar;