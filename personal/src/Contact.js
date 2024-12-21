import { Button, Container, Input, Navbar, Row } from 'reactstrap';
import Track from './images/Track.jpg';
import { Link } from 'react-router-dom';
import Footer from './Footer';
import { useState } from 'react';

function Contact(){
    const [name,setName]=useState('');
    const [eMail,setEMail]=useState('');
    const [message,setMessage]=useState('');
    return(
        <>
            <Navbar color='dark'>
                <Button className='bg-light'><Link to='/' className='text-dark text-decoration-none'>Home</Link></Button>
            </Navbar>
            <div className='d-flex'>
                <div>
                    <img src={Track} alt='Track' className='contactImageCustom'/>
                    <p>Photo: Autumn Bernava</p>
                </div>
                <Container>
                    <Row>Contact</Row>
                    <Row><Input placeholder={"Name"} value={name} onChange={(e)=>setName(e.target.value)}/></Row>
                    <Row><Input placeholder={"E-Mail"} value={eMail} onChange={(e)=>setEMail(e.target.value)}/></Row>
                    <Row><textarea placeholder={"Message"} value={message} onChange={(e)=>setMessage(e.target.value)}/></Row>
                    <Row><Button>Submit</Button></Row>
                </Container>
            </div>
            <Footer/>
        </>
    );
}

export default Contact;