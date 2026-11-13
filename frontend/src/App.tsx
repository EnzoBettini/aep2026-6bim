import { BrowserRouter, Route, Routes } from "react-router-dom";
import { Layout } from "./components/Layout";
import { CidadaosPage } from "./pages/CidadaosPage";
import { HomePage } from "./pages/HomePage";
import { SolicitacaoDetalhePage } from "./pages/SolicitacaoDetalhePage";
import { SolicitacoesPage } from "./pages/SolicitacoesPage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<HomePage />} />
          <Route path="cidadaos" element={<CidadaosPage />} />
          <Route path="solicitacoes" element={<SolicitacoesPage />} />
          <Route path="solicitacoes/:id" element={<SolicitacaoDetalhePage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
