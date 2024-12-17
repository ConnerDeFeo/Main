import React from 'react';
import { Component } from 'react';
import { Container} from 'reactstrap';

class Headings extends Component{
    render(){
        return(
            <Container>
                <h1 className='text-center'>Nightclub Capacity</h1>
                <h3 className='text-center'>Each time someone enters/ leaves the club, select the correct club and click the appropriate button</h3>
            </Container>
        );
    }
}

export default Headings;