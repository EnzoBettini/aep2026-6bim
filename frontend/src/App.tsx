import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from "./components/Layout";
import { HomePage } from "./pages/HomePage";
import { SolicitacaoDetalhePage } from "./pages/SolicitacaoDetalhePage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<HomePage />} />
          <Route path="solicitacoes/:id" element={<SolicitacaoDetalhePage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
