import {Container, Form, Input, Row } from 'reactstrap';
import Track from './images/Track.jpg';
import Footer from './Footer';
import { useState } from 'react';
import emailjs from 'emailjs-com';
import NavigationBar from './NavigationBar';

function Contact({scrollToSection,sectionRef}){
    const [name,setName]=useState('');
    const [eMail,setEMail]=useState('');
    const [message,setMessage]=useState('');

    const sendEmail=(e)=>{
        //Once published, add reCaptcha.
        e.preventDefault();
        emailjs.sendForm('service_ho7d22t', 'template_e1yhk0c', e.target, 'dHmN6Poxt2OKm6_FU')
          .then((result) => {
              window.location.reload()
          }, (error) => {
              console.log(error.text);
          });
      }

    return(
        <>
            <NavigationBar sectionRef={sectionRef} scrollToSection={scrollToSection}/>
            <div className='d-flex'>
                <div>
                    <img src={Track} alt='Track' className='contactImageCustom'/>
                    <p>Photo: Autumn Bernava</p>
                </div>
                <Container>
                    <Form onSubmit={sendEmail}>
                        <Row>Contact</Row>
                        <Row><Input placeholder={"Name"} type='text' name='from_name' value={name} onChange={(e)=>setName(e.target.value)}/></Row>
                        <Row><Input placeholder={"E-Mail"} type='text' value={eMail} name='from_email' onChange={(e)=>setEMail(e.target.value)}/></Row>
                        <Row><textarea placeholder={"Message"} type='text' name='message' value={message} onChange={(e)=>setMessage(e.target.value)}/></Row>
                        <Row><Input type='submit' value='Submit' className='bg-secondary text-light'/></Row>
                    </Form>
                </Container>
            </div>
            <Footer/>
        </>
    );
}

export default Contact;