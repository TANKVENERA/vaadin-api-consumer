import {PolymerElement,html} from '@polymer/polymer';
import '@polymer/paper-tooltip/paper-tooltip.js';

 class Player extends PolymerElement {
    static get template() {
       return html`
        <style>
           .circle {
                cursor: pointer;
           }
           paper-tooltip {
               --paper-tooltip: {
                    font-size: 15px;
                }
           }
        </style>
       <div>
           <img src="[[src]]" id="id_2" alt="" class="circle"/>
           <paper-tooltip for="id_2" offset="0" animation-delay="0">[[playerName]]</paper-tooltip>
       </div>`;
     }

   }

 customElements.define('player-lol', Player);