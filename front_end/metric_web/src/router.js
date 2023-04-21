import VueRouter from "vue-router";
import CK_XML from "@/components/CK_XML";
import CK_JAVA from "@/components/CK_JAVA";
import TRADITION from "@/components/TRADITION";
import LK from "@/components/LK";
import UCP from "@/components/UCP";
import Metrics from "@/components/Metrics";

const routes = [

    {
        path: '/', component: Metrics,
        children:[
            {path: '/CK_XML', component: CK_XML},
            {path: '/CK_JAVA', component: CK_JAVA},
            {path: '/TRADITION', component: TRADITION},
            {path: '/LK', component: LK},
            {path: '/UCP', component: UCP},

        ]


    }
]

const router = new VueRouter({
    routes
})

export default router