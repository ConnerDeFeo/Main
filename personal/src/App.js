import 'bootstrap/dist/css/bootstrap.min.css';
import { Button, Navbar, NavbarText, NavbarToggler } from 'reactstrap';


function App() {
  return (
    <div>
      <Navbar color='secondary' className='d-flex'>
        <NavbarToggler className='navbarMenu'/>
        <div className='ms-auto'>
          <NavbarText className='text-light navSpacing'>Conner DeFeo | Rochester,NY | Full-Stack Developer</NavbarText>
          <Button color='light'>Contact</Button>
        </div>
      </Navbar>
    </div>
  );
}

export default App;
