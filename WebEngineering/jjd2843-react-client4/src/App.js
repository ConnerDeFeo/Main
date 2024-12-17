import { Component } from "react";
import Headings from "./Headings";
import NightClub from "./NightClubs";

class App extends Component{
  render (){
    return(
      <div>
        <Headings/>
        <NightClub/>
      </div>
    );
  }
}

export default App;
